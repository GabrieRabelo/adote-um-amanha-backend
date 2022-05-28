package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.RecusarMatchRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Match")
public interface MatchControllerApi {

    @ApiOperation(value = "Serviço para cadastro de um novo match automático",
            notes = "Serviço responsável por cadastrar um match automático pelo doador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Doação vinculada realizada com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> matchDoador(@PathVariable final Long idNecessidade,
                                     @RequestBody final CadastrarPedidoRequest request,
                                     @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para cadastro de um novo match manual",
            notes = "Serviço responsável por cadastrar um match pelo administrador do sistema")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Match realizado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> matchAdmin(@PathVariable("idNecessidade") final Long idNecessidade,
                                    @PathVariable("idDoacao") final Long idDoacao,
                                    @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para recusar match",
            notes = "Serviço responsável por recusar um match pelo seu identificador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Match realizado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> recusarMatch(@PathVariable("idMatch") final Long idMatch,
                                      @AuthenticationPrincipal final UserPrincipal userPrincipal,
                                      @RequestBody final RecusarMatchRequest request);

    @ApiOperation(value = "Serviço para aprovar match",
            notes = "Serviço responsável por aprovar um match pelo seu identificador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Match realizado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> aprovarMatch(@PathVariable("idMatch") final Long idMatch,
                                      @AuthenticationPrincipal final UserPrincipal userPrincipal);
}