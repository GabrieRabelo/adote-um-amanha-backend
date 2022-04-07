package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.PedidoControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerApi {

    private final PedidoService pedidoService;

    @PostMapping("/pedidos")
    public ResponseEntity<Void> cadastrarPedido(@RequestBody final CadastrarPedidoRequest request,
                                                @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        pedidoService.cadastrar(request, userPrincipal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/necessidades")
    public ResponseEntity<NecessidadesResponse> listarNecessidades(@RequestParam(defaultValue = "0") Integer pagina,
                                                                   @RequestParam(defaultValue = "5") Integer tamanho) {

        return ResponseEntity.ok().body(pedidoService.listarNecessidades(pagina, tamanho));
    }
}

