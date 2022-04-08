package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;
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

    public NecessidadesResponse listarNecessidades(final Integer pagina, final Integer tamanho,
                                                   final String ordenacao, final Direcao direcao) {

        final Pageable paging = PageRequest.of(pagina, tamanho, by(Direction.valueOf(direcao.name()), ordenacao));

        final Page<Pedido> pedidoEntities = repository.findAllByTipoPedido(TipoPedido.NECESSIDADE, paging);

        return necessidadesResponseMapper.apply(pedidoEntities);
    }

    public NecessidadeResponse descricaoNecessidade(final Long id) {

        final Optional<Pedido> pedido = repository.findById(id);

        if (isFalse(pedido.isPresent())) {
            throw new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND);
        }

        return necessidadeResponseMapper.apply(pedido.get());
    }

    public void deletarPedido(final Long id, final UserPrincipal userPrincipal) {

        final Optional<Pedido> optionalPedido = repository.findById(id);

        if (isFalse(optionalPedido.isPresent())) {
            throw new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND);
        }

        final Pedido pedido = optionalPedido.get();

        if (isFalse(PENDENTE.equals(pedido.getStatus()))) {
            throw new ApiException(Mensagem.STATUS_NAO_PENDENTE.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Long usuarioCriadorNecessidade = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);

        if (isFalse(userPrincipal.getId().equals(usuarioCriadorNecessidade))) {
            throw new ApiException(Mensagem.PEDIDO_NAO_PODE_SER_DELETADO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        repository.delete(pedido);
    }
}
