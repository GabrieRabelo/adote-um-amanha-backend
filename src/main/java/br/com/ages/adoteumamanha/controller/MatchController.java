package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.MatchControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.MatchDoadorService;
import br.com.ages.adoteumamanha.service.PedidoService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
public class MatchController implements MatchControllerApi {

    private final MatchDoadorService matchDoadorService;

    // Se for usuário comum:
    // 1 - cadastrar doacao desse usuário (isso é feito automatico, não chamar service)
    // 2 - criar match automatico com doacao recem cadastrada e necessidade com status PENDENTE
    @PostMapping("/match/{idNecessidade}")
    public ResponseEntity<Void> cadastrar(@PathVariable("idNecessidade") final Long idNecessidade,
                                                   @RequestBody final CadastrarPedidoRequest request,
                                                   @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        try {
            matchDoadorService.cadastrar(userPrincipal, idNecessidade, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ADM
    @PostMapping("/{idNecessidade}/vincula/{idDoacao}}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<LoginResponse> cadastrar(@PathVariable("idNecessidade") final Long idNecessidade,
                                                   @PathVariable("idDoacao") final Long idDoacao,
                                                   @RequestBody final CadastrarPedidoRequest request) {

        // Se for adm outro endpoint:
        // 1 - busca necessidades pendentes (que não estão nos matches automaticos)
        // 2 - busca doacoes pendentes (que não estão nos matches automaticos)
        // 3 - mostra essas infos. quando adm junta necessidade com doação cria match com status FINALIZADA
        throw new NotImplementedException("A ser implementado na sprint 3 ou 4 =D");
    }

}