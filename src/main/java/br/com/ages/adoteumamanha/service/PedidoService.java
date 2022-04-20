package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.NecessidadeResponseMapper;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final NecessidadesResponseMapper necessidadesResponseMapper;
    private final NecessidadeResponseMapper necessidadeResponseMapper;
    private final PedidoMapper pedidoMapper;

    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final Pedido entity = pedidoMapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }

    //TODO Deixar o método generico passando também o tipo do pedido, assim irá consultar necessidade e doação.
    public NecessidadesResponse listarNecessidades(final Integer pagina, final Integer tamanho,
                                                   final String ordenacao, final Direcao direcao, final Status status) {

        log.info("pagina {}, tamanho {}, ordenacao {}, direcao {}, status {}", pagina, tamanho, ordenacao, direcao, status);
        final Pageable paging = PageRequest.of(pagina, tamanho, by(Direction.valueOf(direcao.name()), ordenacao));

        log.info("Buscando no banco pedidos paginados");
        if(status == null){
            final Page<Pedido> pedidoEntities = repository.findAllByTipoPedido(TipoPedido.NECESSIDADE, paging);
            return necessidadesResponseMapper.apply(pedidoEntities);
        }
        final Page<Pedido> pedidoEntities = repository.findAllByTipoPedidoAndStatus(TipoPedido.NECESSIDADE, status, paging);
        return necessidadesResponseMapper.apply(pedidoEntities);
    }

    public NecessidadeResponse descricaoNecessidade(final Long id) {

        final Pedido pedido = buscarPedidoPeloID(id);

        return necessidadeResponseMapper.apply(pedido);
    }

    public void deletarPedido(final Long id, final UserPrincipal userPrincipal) {

        final Pedido pedido = buscarPedidoPeloID(id);

        validaStatusPedido(pedido);

        final Long usuarioCriadorNecessidade = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);

        validaUsuarioCriadorPedido(userPrincipal, usuarioCriadorNecessidade);

        log.info("Deletando pedido com id: {}", pedido.getId());
        repository.delete(pedido);
    }

    public NecessidadeResponse atualizarPedido(final Long id, final AtualizarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando se request não é nula");
        if (isNull(request)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Pedido pedido = buscarPedidoPeloID(id);

        validaStatusPedido(pedido);

        final Long usuarioCriadorNecessidade = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);

        validaUsuarioCriadorPedido(userPrincipal, usuarioCriadorNecessidade);

        ofNullable(request).ifPresent(req -> {
            ofNullable(req.getAssunto()).ifPresent(assunto -> pedido.setAssunto(assunto));
            ofNullable(req.getDescricao()).ifPresent(descricao -> pedido.setDescricao(descricao));
            ofNullable(req.getCategoria()).ifPresent(categoria -> pedido.setCategoria(categoria));
            ofNullable(req.getSubcategoria()).ifPresent(subcategoria -> pedido.setSubcategoria(subcategoria));
            ofNullable(req.getUrlVideo()).ifPresent(url -> pedido.setUrlVideo(url));
        });

        log.info("Atualizando pedido com id: {}", pedido.getId());
        return necessidadeResponseMapper.apply(repository.save(pedido));
    }

    private Pedido buscarPedidoPeloID(Long id) {
        log.info("Buscando no pedido com id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
    }

    private void validaUsuarioCriadorPedido(final UserPrincipal userPrincipal, final Long usuarioCriadorNecessidade) {
        log.info("Validando se usuário logado é o criador do pedido. ID: {}", usuarioCriadorNecessidade);
        if (isFalse(userPrincipal.getId().equals(usuarioCriadorNecessidade))) {
            throw new ApiException(Mensagem.PEDIDO_NAO_PODE_SER_DELETADO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }

    private void validaStatusPedido(final Pedido pedido) {
        log.info("Validando status do pedido {}", pedido.getStatus());
        if (isFalse(PENDENTE.equals(pedido.getStatus()))) {
            throw new ApiException(Mensagem.STATUS_NAO_PENDENTE.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
