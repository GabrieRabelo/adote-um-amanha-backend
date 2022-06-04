package br.com.ages.adoteumamanha.service.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String[] emails, String subject, String text) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mail, true);

            message.setFrom("adoteumamanha@gmail.com");
            message.setTo(emails);
            message.setSubject(subject);
            message.setText(text, false);

            javaMailSender.send(mail);

        } catch (Exception e) {
            log.error("Erro ao enviar email: ", e);
        }
    }


}
