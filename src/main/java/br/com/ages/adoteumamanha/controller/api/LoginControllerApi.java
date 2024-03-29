package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Login")
public interface LoginControllerApi {

    @ApiOperation(value = "Serviço para a realização de autenticação na api",
            notes = "Serviço responsável por gerar um Json Web Token para autorização do usuário nos endpoints")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request);
}