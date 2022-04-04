package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.domain.enumeration.Pedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoEntityMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoEntityMapper pedidoEntityMapper;
    private final NecessidadesResponseMapper necessidadesResponseMapper;

    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final PedidoEntity entity = pedidoEntityMapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }

    public List<PedidoEntity> listarNecessidades(int page, int size) {

        Pageable paging = PageRequest.of(page, size);

        final List<PedidoEntity> pedidoEntities = repository.findAllByPedido(Pedido.NECESSIDADE, paging);

        return pedidoEntities;

        //return NecessidadesResponse.builder()
                //.withNecessidades(necessidadesResponseMapper.apply(pedidoEntities))
                //.build();
    }
}
