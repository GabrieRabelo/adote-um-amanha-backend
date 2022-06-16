package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.UsuarioControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.usuarios.BuscarUsuarioService;
import br.com.ages.adoteumamanha.service.usuarios.CadastrarUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;

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

    @GetMapping(value = "/usuario/{id}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<InformacaoUsuarioResponse> buscarUsuarioPorId(@PathVariable final Long id) {
        return ResponseEntity.ok(buscarUsuarioService.buscarInformacoesUsuarioPorId(id));
    }

}


