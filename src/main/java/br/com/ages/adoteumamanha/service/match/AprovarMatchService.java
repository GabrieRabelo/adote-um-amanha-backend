package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class AprovarMatchService {

    private final MatchRepository matchRepository;

    public void aprovar(final Long idMatch, final UserPrincipal userPrincipal) {

        log.info("Buscando o match com id {} no banco", idMatch);
        final Match match = matchRepository.findById(idMatch)
                .orElseThrow(() -> new ApiException(Mensagem.MATCH_INEXISTENTE.getDescricao(), HttpStatus.NOT_FOUND));

        final Pedido necessidade = match.getNecessidade();
        final Pedido doacao = match.getDoacao();

        if (Status.FINALIZADA.equals(match.getStatus()) || Status.RECUSADO.equals(match.getStatus())) {
            throw new ApiException(Mensagem.MATCH_FINALIZADA_OU_RECUSADA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        log.info("Alterado status da necessidade com id {} finalizada", doacao.getId());
        necessidade.setStatus(Status.FINALIZADA);

        log.info("Alterado status da doação com id {} finalizada", doacao.getId());
        doacao.setStatus(Status.FINALIZADA);

        log.info("Alterado atributos do match com id {} referente a sua aprovação", match.getId());
        match.setStatus(Status.FINALIZADA);
        match.setDataTermino(LocalDateTime.now());
        match.setFinalizadoPor(userPrincipal.getEmail());

        log.info("Salvando no banco o match");
        matchRepository.save(match);

    }
}


