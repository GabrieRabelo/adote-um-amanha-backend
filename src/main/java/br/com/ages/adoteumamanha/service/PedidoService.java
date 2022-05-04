package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.DoacaoResponse;
import br.com.ages.adoteumamanha.dto.response.NecessidadeResponse;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.DoacaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.NecessidadeResponseMapper;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final NecessidadesResponseMapper necessidadesResponseMapper;
    private final NecessidadeResponseMapper necessidadeResponseMapper;
    private final DoacaoResponseMapper doacaoResponseMapper;
    private final PedidoMapper pedidoMapper;

    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final Pedido entity = pedidoMapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }

    public NecessidadesResponse listarPedidos(final Integer pagina,
                                              final Integer tamanho, final String ordenacao,
                                              final Direcao direcao,
                                              final List<Categoria> categorias,
                                              final List<Subcategoria> subcategorias,
                                              final List<Status> status,
                                              final Integer mesesCorte,
                                              final String textoBusca,
                                              final TipoPedido tipoPedido) {

        final LocalDateTime mesesDeCorte = ofNullable(mesesCorte).map(meses -> LocalDateTime.now().minusMonths(meses)).orElse(null);

        log.info("pagina {}, tamanho {}, ordenacao {}, direcao {}", pagina, tamanho, ordenacao, direcao);
        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));

        log.info("categorias {}, subcategorias {}, status {}, dataCorte {}, texto de busca {}", categorias, subcategorias, status, mesesDeCorte, textoBusca);
        log.info("Buscando no banco pedidos paginados");
        final Page<Pedido> pedidoEntities = repository.findAllPedidosPorFiltros(categorias, subcategorias, status, mesesDeCorte, textoBusca, tipoPedido, paging);

        return necessidadesResponseMapper.apply(pedidoEntities);
    }

    public NecessidadeResponse descricaoNecessidade(final Long id) {
        final Pedido pedido = buscarNecessidadePorId(id);
        return necessidadeResponseMapper.apply(pedido);
    }

    public DoacaoResponse descricaoDoacao(final Long id, final UserPrincipal usuario) {
        final Pedido doacao = buscarDoacaoPorId(id);

        if (!Objects.equals(doacao.getUsuario().getId(), usuario.getId()) && !usuario.isAdmin()) {
            throw new ApiException(Mensagem.ACESSO_DOACAO_NAO_PERMITIDA.getDescricao(), HttpStatus.UNAUTHORIZED);
        }

        return doacaoResponseMapper.apply(doacao);
    }

    public void deletarPedido(final Long id, final UserPrincipal userPrincipal) {

        final Pedido pedido = buscarNecessidadePorId(id);

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

        final Pedido pedido = buscarNecessidadePorId(id);

        validaStatusPedido(pedido);

        final Long usuarioCriadorNecessidade = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);

        validaUsuarioCriadorPedido(userPrincipal, usuarioCriadorNecessidade);

        Optional.of(request).ifPresent(req -> {
            ofNullable(req.getAssunto()).ifPresent(pedido::setAssunto);
            ofNullable(req.getDescricao()).ifPresent(pedido::setDescricao);
            ofNullable(req.getCategoria()).ifPresent(pedido::setCategoria);
            ofNullable(req.getSubcategoria()).ifPresent(pedido::setSubcategoria);
            ofNullable(req.getUrlVideo()).ifPresent(pedido::setUrlVideo);
        });

        log.info("Atualizando pedido com id: {}", pedido.getId());
        return necessidadeResponseMapper.apply(repository.save(pedido));
    }

    private Pedido buscarNecessidadePorId(Long id) {
        log.info("Buscando no pedido com id: {}", id);
        return repository.findByIdAndTipoPedido(id, NECESSIDADE)
                .orElseThrow(() -> new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
    }

    private Pedido buscarDoacaoPorId(Long id) {
        log.info("Buscando no pedido com id: {}", id);
        return repository.findByIdAndTipoPedido(id, DOACAO)
                .orElseThrow(() -> new ApiException(Mensagem.DOACAO_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
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
