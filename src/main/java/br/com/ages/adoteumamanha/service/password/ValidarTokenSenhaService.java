package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import com.google.common.base.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidarTokenSenhaService {

    @Value("${resetar-password-token.ttl}")
    private Long resetarSenhaTokenTtl;

    public void validarToken(final ResetarSenha resetarSenha, final String token) {

        if (isFalse(Objects.equal(resetarSenha.getToken(), token))) {
            throw new ApiException(Mensagem.TOKEN_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (resetarSenha.getUsado()) {
            throw new ApiException(Mensagem.TOKEN_USADO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Long minutosVidaToken = Duration.between(resetarSenha.getData(), LocalDateTime.now()).toMinutes();

        if (minutosVidaToken > resetarSenhaTokenTtl) {
            throw new ApiException(Mensagem.TOKEN_EXPIRADO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}


