package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarPedidoService {

    private final PedidoMapper pedidoMapper;
    private final PedidoRepository repository;
    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro do pedido");
        validator.validar(request);

        final Pedido entity = pedidoMapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuário com id: {}", userPrincipal.getId());
        repository.save(entity);
    }
}