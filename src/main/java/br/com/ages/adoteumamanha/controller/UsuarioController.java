package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.UsuarioControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioControllerApi {


    private final UsuarioService usuarioService;


    @GetMapping(value = "/public/casas/{id}")
    public ResponseEntity<CasaDescricaoResponse> buscaCasaPorId(@PathVariable final Long id) {

        return ResponseEntity.ok(usuarioService.buscaCasaDescricao(id));
    }

    @GetMapping(value = "/usuario")
    public ResponseEntity<UsuarioResponse> buscaUsuarioAutenticado(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(usuarioService.buscaUsuario(userPrincipal.getId()));
    }

    @PostMapping("/public/usuario")
    public ResponseEntity<Void> cadastrarDoador(@RequestBody final CadastrarUsuarioRequest request) {

        usuarioService.cadastrar(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


