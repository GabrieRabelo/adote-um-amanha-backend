package br.com.ages.adoteumamanha.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public String sendEmail() {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mail, true);

            message.setFrom("adoteumamanha@gmail.com");
            message.setTo("adoteumamanha@gmail.com");
            message.setSubject("subject");
            message.setText("Sample message", false);

            javaMailSender.send(mail);

        } catch (Exception e) {
            return(e.getMessage());
        }

        return "ok";
    }


}
