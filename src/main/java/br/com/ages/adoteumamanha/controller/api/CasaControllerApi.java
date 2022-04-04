package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Casa")
public interface CasaControllerApi {

    @ApiOperation(value = "Serviço para buscar os detalhes de uma casa",
            notes = "Serviço para buscar os detalhes de uma casa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<CasaDescricaoResponse> buscaCasaPorId(@PathVariable Long id);
}