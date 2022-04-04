package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.PedidoControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerApi {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarPedido(@RequestBody final CadastrarPedidoRequest request,
                                                @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        pedidoService.cadastrar(request, userPrincipal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

