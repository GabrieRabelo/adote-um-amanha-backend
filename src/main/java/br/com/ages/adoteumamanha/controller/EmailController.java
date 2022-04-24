package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.service.MailService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

/* !!! THIS CONTROLLER SHOULD BE DELETED ONCE TESTED ON HOMOLOGATION ENVIRONMENT !!! */

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final MailService mailService;

    @GetMapping(value = "/public/email")
    public ResponseEntity<String> mailjet() {
        return ResponseEntity.ok(mailService.sendEmail());
    }

}