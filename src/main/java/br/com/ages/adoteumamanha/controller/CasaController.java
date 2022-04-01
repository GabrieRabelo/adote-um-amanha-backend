package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.LoginControllerApi;
import br.com.ages.adoteumamanha.dto.request.CadastrarNecessidadeRequest;
import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import br.com.ages.adoteumamanha.security.AuthenticationService;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/casa")
public class CasaController implements LoginControllerApi {

    private final AuthenticationService service;

    @PostMapping("/{idCasa}/necessidade")
    public LoginResponse cadastrarNecessidade(@PathVariable final Long idCasa, @RequestBody final CadastrarNecessidadeRequest request) {

        final UserPrincipal usuarioLogado = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return null;

    }
}

