package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
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
            notes = "Serviço responsável por cadastrar um match automático, criado pelo usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> cadastrar(@PathVariable final Long idNecessidade,
                                   @RequestBody final CadastrarPedidoRequest request,
                                   @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

}