package br.com.ages.adoteumamanha.service.usuarios;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.UsuarioMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.validator.CadastrarUsuarioRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarUsuarioService {

    private final CadastrarUsuarioRequestValidator validator;
    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;

    public void cadastrar(final CadastrarUsuarioRequest request, final Perfil perfil) {

        log.info("Validando request de cadastro de doador");
        validator.validar(request);

        if(repository.existsByEmailOrDocumento(request.getEmail(), request.getDocumento())) {
            log.error("{}: E-mail: {}, CPF: {}", Mensagem.EMAIL_OU_CPF_JA_CADASTRADO.getDescricao(), request.getEmail(), request.getDocumento());
            throw new ApiException(Mensagem.EMAIL_OU_CPF_JA_CADASTRADO.getDescricao(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final Usuario entity = mapper.apply(request, perfil);

        log.info("Cadastrando usuário e-mail {} com perfil {}", request.getEmail(), perfil);
        repository.save(entity);
    }
}
