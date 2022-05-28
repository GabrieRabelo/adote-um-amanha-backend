package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.ResumoMatchResponseMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarMatchService {

    private final MatchRepository repository;
    private final ResumoMatchResponseMapper resumoMatchResponseMapper;

    public ResumoMatchResponse buscarPorID(final Long id) {
        log.info("Buscando match por Id: {}", id);
        final Match match = repository.findById(id)
                .orElseThrow(() -> new ApiException(Mensagem.MATCH_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
        return resumoMatchResponseMapper.apply(match);
    }
}

