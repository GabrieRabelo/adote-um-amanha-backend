package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.UsuarioControllerApi;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuariosResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.usuarios.BuscarUsuarioService;
import br.com.ages.adoteumamanha.service.usuarios.CadastrarUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;
import static org.springframework.data.domain.Sort.by;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioControllerApi {

    private final BuscarUsuarioService buscarUsuarioService;
    private final CadastrarUsuarioService cadastrarUsuarioService;

    @GetMapping(value = "/public/casas/{id}")
    public ResponseEntity<CasaResponse> buscarCasaPorId(@PathVariable final Long id) {
        return ResponseEntity.ok(buscarUsuarioService.buscarCasaPorId(id));
    }

    @GetMapping(value = "/usuario")
    public ResponseEntity<UsuarioResponse> buscarUsuarioAutenticado(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(buscarUsuarioService.buscarUsuarioPorId(userPrincipal.getId()));
    }

    @PostMapping("/public/usuario")
    public ResponseEntity<Void> cadastrarDoador(@RequestBody final CadastrarUsuarioRequest request) {
        cadastrarUsuarioService.cadastrar(request, DOADOR);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/casa")
    public ResponseEntity<Void> cadastrarCasa(@RequestBody final CadastrarUsuarioRequest request) {
        cadastrarUsuarioService.cadastrar(request, CASA);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/usuario/{id}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<InformacaoUsuarioResponse> buscarUsuarioPorId(@PathVariable final Long id) {
        return ResponseEntity.ok(buscarUsuarioService.buscarInformacoesUsuarioPorId(id));
    }

    @GetMapping(value ="/usuarios")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<ResumoUsuariosResponse> buscarListaDeUsuarios(@RequestParam(defaultValue = "0") final Integer pagina,
                                                                        @RequestParam(defaultValue = "5") final Integer tamanho,
                                                                        @RequestParam(defaultValue = "ASC") final Direcao direcao,
                                                                        @RequestParam(defaultValue = "nome") final String ordenacao,
                                                                        @RequestParam(defaultValue = "", required = false) final String nome) {
        final Pageable page = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));
        return ResponseEntity.ok().body(buscarUsuarioService.buscarDoadores(nome, page));
    }

}


