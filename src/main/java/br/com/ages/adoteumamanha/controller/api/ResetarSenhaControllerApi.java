package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.dto.request.NovaSenhaRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ResetarSenhaControllerApi {

    @ApiOperation(value = "Serviço para gerar token para resetar senha",
            notes = "Serviço responsável por gerar um token de resete de senha e enviar por e-mail")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Token gerado e enviado"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> gerarToken(@RequestParam(value = "email") final String email);

    @ApiOperation(value = "Serviço para resetar a senha do usuário",
            notes = "Serviço responsável por resetar a senha do usário dado um e-mail e token gerado anteriormente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Senha resetada com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> resetarSenha(@RequestParam(value = "email") final String email,
                                      @RequestParam(value = "token") final String token,
                                      @RequestBody final NovaSenhaRequest request);

}
