package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.MatchControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.match.MatchAdminService;
import br.com.ages.adoteumamanha.service.match.MatchDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Arrays.asList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController implements MatchControllerApi {

    private final MatchDoadorService matchDoadorService;
    private final MatchAdminService matchAdminService;

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
    public ResponseEntity<Long> matchAdmin(@PathVariable("idNecessidade") final Long idNecessidade,
                                           @PathVariable("idDoacao") final Long idDoacao,
                                           @AuthenticationPrincipal final UserPrincipal userPrincipal) {

        return new ResponseEntity<>(matchAdminService.cadastrar(idDoacao, idNecessidade, userPrincipal), HttpStatus.CREATED);
    }

}