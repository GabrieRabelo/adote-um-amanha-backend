package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.PedidoControllerApi;
import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.RecusarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.AtualizarPedidoService;
import br.com.ages.adoteumamanha.service.pedidos.CadastrarPedidoService;
import br.com.ages.adoteumamanha.service.pedidos.DeletarPedidoService;
import br.com.ages.adoteumamanha.service.pedidos.RecusarPedidoService;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoService;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoStrategy;
import br.com.ages.adoteumamanha.service.pedidos.filtros.BuscarPedidosComFiltrosService;
import br.com.ages.adoteumamanha.service.pedidos.filtros.BuscarPedidosComFiltrosStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@RestController
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerApi {

    private final AtualizarPedidoService atualizarPedidoService;
    private final CadastrarPedidoService cadastrarPedidoService;
    private final DeletarPedidoService deletarPedidoService;
    private final BuscarPedidosComFiltrosService buscarPedidosComFiltrosService;
    private final BuscarPedidosComFiltrosStrategy buscarPedidosComFiltrosStrategy;
    private final BuscarDescricaoPedidoService buscarDescricaoPedidoService;
    private final BuscarDescricaoPedidoStrategy buscarDescricaoPedidoStrategy;
    private final RecusarPedidoService recusarPedidoService;

    @PostMapping("/pedidos")
    @RolesAllowed({"CASA", "DOADOR"})
    public ResponseEntity<Void> cadastrar(@RequestBody final CadastrarPedidoRequest request,
                                          @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        cadastrarPedidoService.cadastrar(request, userPrincipal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/pedidos/{id}")
    @RolesAllowed({"CASA", "DOADOR"})
    public ResponseEntity<Void> atualizar(@PathVariable("id") final Long idPedido,
                                          @RequestBody final AtualizarPedidoRequest request,
                                          @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        atualizarPedidoService.atualizar(idPedido, request, userPrincipal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/pedidos/{id}")
    @RolesAllowed({"CASA", "DOADOR"})
    public ResponseEntity<Void> deletar(@PathVariable("id") final Long idNecessidade,
                                        @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        deletarPedidoService.deletar(idNecessidade, userPrincipal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/necessidades")
    public ResponseEntity<PedidosResponse> buscarNecessidades(@RequestParam(defaultValue = "0") final Integer pagina,
                                                              @RequestParam(defaultValue = "5") final Integer tamanho,
                                                              @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                              @RequestParam(defaultValue = "dataHora") final String ordenacao,
                                                              @RequestParam(defaultValue = "", required = false) final String textoBusca,
                                                              @RequestParam(required = false) final List<Categoria> categorias,
                                                              @RequestParam(required = false) final List<Subcategoria> subcategorias,
                                                              @RequestParam(required = false) final Integer mesesCorte) {

        return ResponseEntity.ok().body(buscarPedidosComFiltrosService.buscar(pagina, tamanho, ordenacao, direcao,
                categorias, subcategorias, asList(PENDENTE), mesesCorte, textoBusca, NECESSIDADE, null));
    }

    @GetMapping("/pedidos")
    @RolesAllowed({"DOADOR", "ADMIN", "CASA"})
    public ResponseEntity<PedidosResponse> buscarPedidos(@RequestParam(defaultValue = "0") final Integer pagina,
                                                         @RequestParam(defaultValue = "5") final Integer tamanho,
                                                         @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                         @RequestParam(defaultValue = "dataHora") final String ordenacao,
                                                         @RequestParam(defaultValue = "", required = false) final String textoBusca,
                                                         @RequestParam(required = false) final List<Categoria> categorias,
                                                         @RequestParam(required = false) final List<Subcategoria> subcategorias,
                                                         @RequestParam(required = false) final List<Status> status,
                                                         @RequestParam(required = false) final Integer mesesCorte,
                                                         @RequestParam final TipoPedido tipoPedido,
                                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok().body(buscarPedidosComFiltrosStrategy.run(userPrincipal.getPerfil(), tipoPedido)
                .buscar(pagina, tamanho, ordenacao, direcao, categorias, subcategorias, status, mesesCorte, textoBusca, userPrincipal.getId()));
    }

    @GetMapping("/public/necessidades/{id}")
    public ResponseEntity<DescricaoPedidoResponse> buscarDescricaoNecessidade(@PathVariable final Long id) {
        return ResponseEntity.ok().body(buscarDescricaoPedidoService.buscar(id, singletonList(NECESSIDADE), null));
    }

    @GetMapping("/pedidos/{id}")
    @RolesAllowed({"CASA", "DOADOR", "ADMIN"})
    public ResponseEntity<DescricaoPedidoResponse> buscarDescricaoPedido(@PathVariable final Long id,
                                                                         @RequestParam final TipoPedido tipoPedido,
                                                                         @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        return ResponseEntity.ok().body(buscarDescricaoPedidoStrategy.run(userPrincipal.getPerfil(), tipoPedido)
                .executar(id, userPrincipal.getId()));
    }

    @PatchMapping("/pedidos/{id}/recusar")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<Void> recusarPedido(@PathVariable final Long id,
                                              @RequestBody RecusarPedidoRequest recusarPedidoRequest) {

        recusarPedidoService.recusar(id, recusarPedidoRequest.getMotivoRecusa());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

