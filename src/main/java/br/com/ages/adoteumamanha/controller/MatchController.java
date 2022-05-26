package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.MatchControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.match.BuscarMatchService;
import br.com.ages.adoteumamanha.service.match.MatchAdminService;
import br.com.ages.adoteumamanha.service.match.MatchDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController implements MatchControllerApi {

    private final MatchDoadorService matchDoadorService;
    private final MatchAdminService matchAdminService;
    private final BuscarMatchService buscarMatchService;

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

    @GetMapping("/{idMatch}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<ResumoMatchResponse> buscarMatch(@PathVariable("idMatch") final Long idMatch) {
        buscarMatchService.buscarPorID(idMatch);
        return ResponseEntity.ok().body(buscarMatchService.buscarPorID(idMatch));
    }

}