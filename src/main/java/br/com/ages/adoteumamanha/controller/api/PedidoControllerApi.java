package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
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
import springfox.documentation.annotations.ApiIgnore;

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
    ResponseEntity<Void> cadastrar(@RequestBody final CadastrarPedidoRequest request,
                                   @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para atualizar um pedido especifico",
            notes = "Serviço responsável por atualizar um pedido com status pendente dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> atualizar(@PathVariable final Long id,
                                   @RequestBody final AtualizarPedidoRequest request,
                                   @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço para deletar um pedido especifico",
            notes = "Serviço responsável por deletar um pedido com status pendente dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> deletar(@PathVariable final Long id,
                                 @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);


    @ApiOperation(value = "Serviço para listagem de necessidades",
            notes = "Serviço responsável por listar as necessidades")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<PedidosResponse> buscarNecessidades(@RequestParam(defaultValue = "0") final Integer pagina,
                                                       @RequestParam(defaultValue = "5") final Integer tamanho,
                                                       @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                       @RequestParam(defaultValue = "dataHora") final String ordenacao,
                                                       @RequestParam(defaultValue = "", required = false) final String textoBusca,
                                                       @RequestParam(required = false) final List<Categoria> categorias,
                                                       @RequestParam(required = false) final List<Subcategoria> subcategorias,
                                                       @RequestParam(required = false) final Integer mesesCorte);

    @ApiOperation(value = "Serviço para listagem de pedidos",
            notes = "Serviço responsável por listar de pedidos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<PedidosResponse> buscarPedidos(@RequestParam(defaultValue = "0") final Integer pagina,
                                                  @RequestParam(defaultValue = "5") final Integer tamanho,
                                                  @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                  @RequestParam(defaultValue = "dataHora") final String ordenacao,
                                                  @RequestParam(defaultValue = "", required = false) final String textoBusca,
                                                  @RequestParam(required = false) final List<Categoria> categorias,
                                                  @RequestParam(required = false) final List<Subcategoria> subcategorias,
                                                  @RequestParam(required = false) final List<Status> status,
                                                  @RequestParam(required = false) final Integer mesesCorte,
                                                  @RequestParam(required = false) final TipoPedido tipoPedido,
                                                  @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço buscar uma necessidade especifica",
            notes = "Serviço responsável por buscar a descrição de uma necessidade dado um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<DescricaoPedidoResponse> buscarDescricaoNecessidade(@PathVariable final Long id);

    @ApiOperation(value = "Serviço buscar um pedido especifico",
            notes = "Serviço responsável por buscar a descrição de um pedido especifico um id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<DescricaoPedidoResponse> buscarDescricaoPedido(@PathVariable final Long id,
                                                                  @RequestParam final TipoPedido tipoPedido,
                                                                  @ApiIgnore @AuthenticationPrincipal final UserPrincipal userPrincipal);

}