package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UsuarioController implements UsuarioControllerApi {

    @Autowired
    private final UsuarioService usuarioService;
    @Autowired

    private final UsuarioRepository usuarioRepository;
    @GetMapping(value = "/public/casas/{id}")
    public ResponseEntity<CasaDescricaoResponse> buscaCasaPorId(@PathVariable final Long id) {

        return ResponseEntity.ok(usuarioService.buscaCasaDescricao(id));
    }

    @GetMapping(value = "/usuario")
    public ResponseEntity<UsuarioResponse> buscaUsuarioAutenticado(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(usuarioService.buscaUsuario(userPrincipal.getId()));
    }

    @PostMapping("/public/users")
    public ResponseEntity<Void> cadastrarDoador(@RequestBody final CadastrarUsuarioRequest request) {

        doadorService.cadastrar(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


>>>>>>> 06516fe7c4a8d89b2445b8f6c99a8df17856aad0
