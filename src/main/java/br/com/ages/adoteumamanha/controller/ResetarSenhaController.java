package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.ResetarSenhaControllerApi;
import br.com.ages.adoteumamanha.dto.request.NovaSenhaRequest;
import br.com.ages.adoteumamanha.service.password.GerarTokenSenhaService;
import br.com.ages.adoteumamanha.service.password.ResetarSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/resetar-senha")
public class ResetarSenhaController implements ResetarSenhaControllerApi {

    private final GerarTokenSenhaService gerarTokenSenhaService;
    private final ResetarSenhaService resetarSenhaService;

    @PostMapping("/gerar-token")
    public ResponseEntity<Void> gerarToken(@RequestParam(value = "email") final String email) {
        gerarTokenSenhaService.gerarToken(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> resetarSenha(@RequestParam(value = "email") final String email,
                                             @RequestParam(value = "token") final String token,
                                             @RequestBody final NovaSenhaRequest request) {

        resetarSenhaService.resetar(email, token, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


