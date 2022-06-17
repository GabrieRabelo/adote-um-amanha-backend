package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.ResetarSenhaRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.mail.MailService;
import br.com.ages.adoteumamanha.util.TokenGeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class GerarTokenSenhaService {

    private final String PATH_RECUPERAR_TOKEN = "#/recover-password?email=%s&token=%s";
    private final String ASSUNTO = "Adote Um Amanhã - Troca de senha";
    private final String CORPO = "<p>Olá, %s! <br><a href=\"%s\">Clique Aqui para trocar a sua senha!</a></p>";
    private final UsuarioRepository usuarioRepository;
    private final ResetarSenhaRepository resetarSenhaRepository;
    private final TokenGeneratorUtils tokenGeneratorUtils;
    private final MailService mailService;
    @Value("${url.adote-um-amanha}")
    private String url;

    public void gerarToken(final String email) {

        final Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(Mensagem.EMAIL_NAO_CADASTRADO.getDescricao(), HttpStatus.NOT_FOUND));

        final String token = tokenGeneratorUtils.generate();

        final ResetarSenha resetarSenha = ResetarSenha.builder()
                .withEmail(usuario.getEmail())
                .withToken(token)
                .withData(LocalDateTime.now())
                .build();

        final String nomeDestinatario = StringUtils.capitalize(usuario.getNome());
        final String urlResetarSenha = String.format(url + PATH_RECUPERAR_TOKEN, email, token);

        resetarSenhaRepository.save(resetarSenha);

        final String corpo = String.format(CORPO, nomeDestinatario, urlResetarSenha);
        this.sendEmailAsync(new String[]{email}, ASSUNTO, corpo, TRUE);
    }

    private void sendEmailAsync(final String[] destinatarios, final String assunto, final String corpo, final Boolean isHtml) {
        new Thread(() -> {
            mailService.sendEmail(destinatarios, assunto, corpo, isHtml);
        }).start();
    }
}



