package br.com.ages.adoteumamanha.service.usuarios;

import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.InformacaoUsuarioResponseMapper;
import br.com.ages.adoteumamanha.mapper.UsuarioResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CasaDescricaoResponseMapper casaDescricaoResponseMapper;
    private final UsuarioResponseMapper usuarioResponseMapper;
    private final InformacaoUsuarioResponseMapper informacaoUsuarioResponseMapper;

    public CasaResponse buscarCasaPorId(final Long id) {
        log.info("Buscando casa pelo id: {}", id);
        return usuarioRepository.findByIdAndPerfil(id, CASA)
                .map(casaDescricaoResponseMapper)
                .orElseThrow(() -> new ApiException(Mensagem.CASA_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
    }

    public UsuarioResponse buscarUsuarioPorId(final Long id) {
        log.info("Buscando usuário pelo id: {}", id);
        return usuarioRepository.findById(id)
                .map(usuarioResponseMapper)
                .orElseThrow(() -> new ApiException(Mensagem.USUARIO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
    }

    public InformacaoUsuarioResponse buscarInformacoesUsuarioPorId(final Long id) {
        log.info("Buscando usuário pelo id: {}", id);

        return usuarioRepository.findById(id)
                //faz a busca do nro de doações
                .map(it -> informacaoUsuarioResponseMapper.apply(it))
                .orElseThrow(() -> new ApiException(Mensagem.USUARIO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
    }
}
