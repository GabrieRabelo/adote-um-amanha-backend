package br.com.ages.adoteumamanha.controller;

import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.ages.adoteumamanha.utils.MailMessage;
import com.mailjet.client.errors.MailjetException;
import org.springframework.http.ResponseEntity;
import com.mailjet.client.MailjetClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final MailjetClient mailjet;

    @GetMapping(value = "/public/email")
    public ResponseEntity<String> mailjet() throws MailjetSocketTimeoutException, MailjetException {
        return ResponseEntity.ok(
            mailjet.post(
                MailMessage.build(
                    "alessandra.dutra@pucrs.br",
                    "Hello sun shine",
                    "We are now sending email!"
                )
            ).getData()
            .toString()
        );
    }

}