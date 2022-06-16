package br.com.ages.adoteumamanha.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    @Value("${mail.remetente}")
    private String emailRemetente;

    public void sendEmail(final String[] destinatarios, final String assunto, final String corpo, final Boolean isHtml) {
        try {

            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mail, true);

            message.setFrom(emailRemetente);
            message.setTo(destinatarios);
            message.setSubject(assunto);
            message.setText(corpo, isHtml);

            javaMailSender.send(mail);

        } catch (Exception e) {
            log.error("Erro ao enviar email: ", e.getMessage());
        }
    }

}
