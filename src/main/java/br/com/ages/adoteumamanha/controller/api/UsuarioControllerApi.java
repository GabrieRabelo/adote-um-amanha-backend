package br.com.ages.adoteumamanha.controller.api;

import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuariosResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.security.RolesAllowed;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;

@Api(tags = "Usuario")
public interface UsuarioControllerApi {

    @ApiOperation(value = "Serviço responsável por buscar os detalhes de uma casa",
            notes = "Serviço para buscar os detalhes de uma casa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<CasaResponse> buscarCasaPorId(@PathVariable Long id);

    @ApiOperation(value = "Serviço responsável por buscar detalhes de um usuário para gerenciar telas",
            notes = "Serviço para buscar detalhes de um usuário para gerenciar telas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<UsuarioResponse> buscarUsuarioAutenticado(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @ApiOperation(value = "Serviço responsável por cadastrar doador",
            notes = "Serviço responsável por cadastrar um doador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Doador criado com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> cadastrarDoador(@RequestBody CadastrarUsuarioRequest request);

    @ApiOperation(value = "Serviço responsável por preencher lista de doadores no front",
            notes = "Serviço responsável por preencher lista de doadores no front")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<ResumoUsuariosResponse> buscarListaDeUsuarios(final Integer pagina,
                                                                 final Integer tamanho,
                                                                 final Direcao direcao,
                                                                 final String ordenacao,
                                                                 final String nome);

    @ApiOperation(value = "Serviço responsável por cadastrar uma instituição",
            notes = "Serviço responsável para que um admin cadastre uma instituição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Instituição criada com sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<Void> cadastrarCasa(@RequestBody final CadastrarUsuarioRequest request);

    @ApiOperation(value = "Serviço responsável por buscar qualquer usuário cadastrado",
            notes = "Serviço responsável para que um admin busque qualquer usuário cadastrado dado seu id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso"),
            @ApiResponse(code = 400, message = "Solicitação Inválida"),
            @ApiResponse(code = 401, message = "Token de acesso inválido"),
            @ApiResponse(code = 403, message = "Acesso proibido"),
            @ApiResponse(code = 500, message = "Erro Interno")
    })
    ResponseEntity<InformacaoUsuarioResponse> buscarUsuarioPorId(@PathVariable final Long id);

}