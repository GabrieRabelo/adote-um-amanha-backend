package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.MatchControllerApi;
import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.match.BuscarMatchesComFiltrosService;
import br.com.ages.adoteumamanha.service.match.MatchAdminService;
import br.com.ages.adoteumamanha.service.match.MatchDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController implements MatchControllerApi {

    private final MatchDoadorService matchDoadorService;
    private final MatchAdminService matchAdminService;

    private final BuscarMatchesComFiltrosService buscarMatchesComFiltrosService;

    @PostMapping("/{idNecessidade}")
    @RolesAllowed("DOADOR")
    public ResponseEntity<Void> matchDoador(@PathVariable("idNecessidade") final Long idNecessidade,
                                            @RequestBody final CadastrarPedidoRequest request,
                                            @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        matchDoadorService.cadastrar(userPrincipal, idNecessidade, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{idNecessidade}/vincula/{idDoacao}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> matchAdmin(@PathVariable("idNecessidade") final Long idNecessidade,
                                           @PathVariable("idDoacao") final Long idDoacao,
                                           @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        matchAdminService.cadastrar(idDoacao, idNecessidade, userPrincipal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<MatchesResponse> buscarMatches(@RequestParam(defaultValue = "0") final Integer pagina,
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

        return ResponseEntity.ok().body(
                buscarMatchesComFiltrosService.buscar(pagina, tamanho, ordenacao, direcao, categorias, subcategorias, status, mesesCorte, textoBusca, userPrincipal.getId())));
    }

}