package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
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
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final CadastrarUsuarioRequestValidator validator;
    private final UsuarioRepository repository;

    public void cadastrar(final CadastrarUsuarioRequest request) {

        log.info("Validando request de cadastro de doador");
        validator.validate(request);

        final Usuario entity = usuarioMapper.apply(request);

        //log.info("Cadastrando doador para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }
}
