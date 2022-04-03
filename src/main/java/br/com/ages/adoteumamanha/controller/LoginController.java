package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.LoginControllerApi;
import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import br.com.ages.adoteumamanha.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/autenticacao")
public class LoginController implements LoginControllerApi {

    private final AuthenticationService service;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody final LoginRequest request) {

        System.out.println(new BCryptPasswordEncoder().encode("marcelo"));
        return service.authenticate(request.getEmail(), request.getSenha());
    }

}

