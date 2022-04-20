package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CasaService {

    private final UsuarioRepository usuarioRepository;
    private final CasaDescricaoResponseMapper casaDescricaoResponseMapper;

    public CasaDescricaoResponse buscaCasaDescricao(final Long id) {

        log.info("Buscando usuário pelo id: {}", id);
        return usuarioRepository.findByIdAndPerfil(id, Perfil.CASA)
                .map(casaDescricaoResponseMapper::apply)
                .orElseThrow(() -> new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));
    }
}
