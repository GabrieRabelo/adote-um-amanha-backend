package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.PedidoEntityMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoEntityMapper mapper;
    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final PedidoEntity entity = mapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }
}
