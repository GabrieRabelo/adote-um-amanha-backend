package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.RecusarMatchRequest;
import br.com.ages.adoteumamanha.dto.response.DescricaoMatchResponse;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
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
    ResponseEntity<DescricaoMatchResponse> matchAdmin(@PathVariable("idNecessidade") final Long idNecessidade,
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

    @ApiOperation(value = "Serviço para buscar um match específico",
            notes = "Serviço responsável por buscar um match específico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Match buscado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<DescricaoMatchResponse> buscarMatch(@PathVariable("idMatch") final Long idMatch);

    @ApiOperation(value = "Serviço para listagem de matches",
            notes = "Serviço responsável por listar matches")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<MatchesResponse> buscarMatches(@RequestParam(defaultValue = "0") final Integer pagina,
                                                  @RequestParam(defaultValue = "5") final Integer tamanho,
                                                  @RequestParam(defaultValue = "DESC") final Direcao direcao,
                                                  @RequestParam(defaultValue = "dataHora") final String ordenacao,
                                                  @RequestParam(defaultValue = "", required = false) final String textoBusca,
                                                  @RequestParam(required = false) final List<Categoria> categorias,
                                                  @RequestParam(required = false) final List<Subcategoria> subcategorias,
                                                  @RequestParam(required = false) final List<Status> status,
                                                  @RequestParam(required = false) final Integer mesesCorte);
}