package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Pedido")
public interface PedidoControllerApi {

    @ApiOperation(value = "Serviço para cadastro de um novo pedido",
            notes = "Serviço responsável por cadastrar uma necessidade ou doação")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> cadastrarPedido(@RequestBody final CadastrarPedidoRequest request,
                                         @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para listagem de necessidades",
            notes = "Serviço responsável por listar as necessidades")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<List<PedidoEntity>> listarNecessidades(int page, int size);
}