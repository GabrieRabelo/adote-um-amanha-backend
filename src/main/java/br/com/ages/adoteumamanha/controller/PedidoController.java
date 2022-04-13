package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.PedidoControllerApi;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;

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

    @DeleteMapping("/pedidos/{id}")
    @RolesAllowed({"CASA", "DOADOR"})
    public ResponseEntity<Void> deletarPedido(@PathVariable("id") final Long idNecessidade,
                                              @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        pedidoService.deletarPedido(idNecessidade, userPrincipal);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/pedidos/{id}")
    @RolesAllowed({"CASA", "DOADOR"})
    public ResponseEntity<NecessidadeResponse> atualizarPedido(@PathVariable("id") final Long idNecessidade,
                                                               @RequestBody final AtualizarPedidoRequest request,
                                                               @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        return ResponseEntity.ok().body(pedidoService.atualizarPedido(idNecessidade, request, userPrincipal));
    }

    @GetMapping("/public/necessidades")
    public ResponseEntity<NecessidadesResponse> listarNecessidades(@RequestParam(defaultValue = "0") final Integer pagina,
                                                                   @RequestParam(defaultValue = "5") final Integer tamanho,
                                                                   @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                                   @RequestParam(defaultValue = "dataHora") final String ordenacao) {

        return ResponseEntity.ok().body(pedidoService.listarNecessidades(pagina, tamanho, ordenacao, direcao));
    }

    @GetMapping("/public/necessidades/{id}")
    public ResponseEntity<NecessidadeResponse> descricaoNecessidade(
            @PathVariable("id") final Long idNecessidade) {

        return ResponseEntity.ok().body(pedidoService.descricaoNecessidade(idNecessidade));
    }
}

