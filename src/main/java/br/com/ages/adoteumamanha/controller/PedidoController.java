package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.PedidoControllerApi;
import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PedidoEntity>> listarNecessidades(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "1") int size) {
        return ResponseEntity.ok().body(pedidoService.listarNecessidades(page, size));
    }
}

