package br.com.ages.adoteumamanha.service.usuarios;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.mapper.UsuarioMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.validator.CadastrarUsuarioRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        validator.validate(request);

        final Usuario entity = mapper.apply(request, perfil);

        log.info("Cadastrando usuário e-mail {} com perfil {}", request.getEmail(), perfil);
        repository.save(entity);
    }
}
