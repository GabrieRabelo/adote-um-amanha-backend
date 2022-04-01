package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.dto.converter.CasaConverter;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CasaService {

    private final UsuarioRepository usuarioRepository;

    public Optional<CasaDescricaoResponse> buscaCasaDescricao(Long id) {
        return usuarioRepository.findById(id)
                .map(CasaConverter::paraResponse);
    }
}
