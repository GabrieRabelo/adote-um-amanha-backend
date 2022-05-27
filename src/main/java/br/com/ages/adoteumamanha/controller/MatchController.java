package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.MatchControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.RecusarMatchRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.match.AprovarMatchService;
import br.com.ages.adoteumamanha.service.match.MatchAdminService;
import br.com.ages.adoteumamanha.service.match.MatchDoadorService;
import br.com.ages.adoteumamanha.service.match.RecusarMatchService;
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
    private final AprovarMatchService aprovarMatchService;
    private final RecusarMatchService recusarMatchService;

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

    @PostMapping("/{idMatch}/aprovar")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> aprovarMatch(@PathVariable("idMatch") final Long idMatch,
                                             @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        aprovarMatchService.aprovar(idMatch, userPrincipal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{idMatch/recusar}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> recusarMatch(@PathVariable("idMatch") final Long idMatch,
                                             @AuthenticationPrincipal final UserPrincipal userPrincipal,
                                             @RequestBody final RecusarMatchRequest request) {

        recusarMatchService.recusar(idMatch, userPrincipal, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}