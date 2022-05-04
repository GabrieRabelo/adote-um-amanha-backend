package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.mapper.UsuarioMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.validator.CadastrarUsuarioRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.UsuarioResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CasaDescricaoResponseMapper casaDescricaoResponseMapper;
    private final UsuarioResponseMapper usuarioResponseMapper;
    private final UsuarioMapper usuarioMapper;
    private final CadastrarUsuarioRequestValidator validator;
    private final UsuarioRepository repository;

    public CasaDescricaoResponse buscaCasaDescricao(final Long id) {

        log.info("Buscando usuário pelo id: {}", id);
        return usuarioRepository.findByIdAndPerfil(id, Perfil.CASA)
                .map(casaDescricaoResponseMapper)
                .orElseThrow(() -> new ApiException(Mensagem.CASA_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
    }

    public UsuarioResponse buscaUsuario(final Long id) {

        log.info("Buscando usuário pelo id: {}", id);
        return usuarioRepository.findById(id)
                .map(usuarioResponseMapper)
                .orElseThrow(() -> new ApiException(Mensagem.USUARIO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
    }



    public void cadastrar(final CadastrarUsuarioRequest request) {

        log.info("Validando request de cadastro de doador");
        validator.validate(request);

        final Usuario entity = usuarioMapper.apply(request);

        //log.info("Cadastrando doador para o usuario com id: {}", userPrincipal.getId());
        repository.save(entity);
    }
}
