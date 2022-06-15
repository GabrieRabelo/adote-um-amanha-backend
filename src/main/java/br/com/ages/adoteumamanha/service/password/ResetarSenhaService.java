package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.NovaSenhaRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.redis.ResetarSenhaRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetarSenhaService {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;
    private final ResetarSenhaRepository resetarSenhaRepository;

    private final ValidarTokenSenhaService validarTokenSenhaService;

    public void resetar(final String email, final String token, final NovaSenhaRequest request) {

        if (isNull(request) || isNull(request.getNovaSenha())) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(Mensagem.USUARIO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));


        final ResetarSenha resetarSenha = resetarSenhaRepository.findByTokenAndEmail(token, email)
                .orElseThrow(() -> new ApiException(Mensagem.TOKEN_INVALIDO.getDescricao(), HttpStatus.NOT_FOUND));

        validarTokenSenhaService.validarToken(resetarSenha, token);

        usuario.setSenha(encoder.encode(request.getNovaSenha()));
        resetarSenha.setUsado(TRUE);

        usuarioRepository.save(usuario);
        resetarSenhaRepository.save(resetarSenha);
    }
}


