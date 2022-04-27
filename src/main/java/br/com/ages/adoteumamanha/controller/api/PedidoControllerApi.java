package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;

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
    ResponseEntity<NecessidadesResponse> listarNecessidades(@RequestParam final Integer pagina,
                                                            @RequestParam final Integer tamanho,
                                                            @RequestParam final Direcao direcao,
                                                            @RequestParam final String ordenacao,
                                                            @RequestParam final Status status);

    @ApiOperation(value = "Serviço buscar uma necessidade especifica",
            notes = "Serviço responsável por buscar a descrição de uma necessidade dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<NecessidadeResponse> descricaoNecessidade(@PathVariable final Long id);

    @ApiOperation(value = "Serviço para deletar um pedido especifico",
            notes = "Serviço responsável por deletar um pedido com status pendente dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> deletarPedido(@PathVariable final Long id, @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para atualizar um pedido especifico",
            notes = "Serviço responsável por atualizar um pedido com status pendente dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<NecessidadeResponse> atualizarPedido(@PathVariable final Long id,
                                                        @RequestBody final AtualizarPedidoRequest request,
                                                        @AuthenticationPrincipal final UserPrincipal userPrincipal);

}