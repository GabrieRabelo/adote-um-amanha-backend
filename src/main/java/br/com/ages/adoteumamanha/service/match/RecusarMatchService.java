package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.request.RecusarMatchRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecusarMatchService {

    private final MatchRepository matchRepository;

    public void recusar(final Long idMatch, final UserPrincipal userPrincipal, final RecusarMatchRequest request) {

        log.info("Validando request");
        if (isNull(request) || isEmpty(request.getMotivoReprovacao())) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        log.info("Buscando o match com id {} no banco", idMatch);
        final Match match = matchRepository.findById(idMatch)
                .orElseThrow(() -> new ApiException(Mensagem.MATCH_INEXISTENTE.getDescricao(), HttpStatus.NOT_FOUND));

        final Pedido necessidade = match.getNecessidade();
        final Pedido doacao = match.getDoacao();

        log.info("Verificando se o match já não foi finalizado ou recusado anteriormente");
        if (Status.FINALIZADA.equals(match.getStatus()) && Status.RECUSADA.equals(match.getStatus())) {
            throw new ApiException(Mensagem.MATCH_FINALIZADA_OU_RECUSADA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        log.info("Alterado status da necessidade com id {} pendente", necessidade.getId());
        necessidade.setStatus(Status.PENDENTE);

        log.info("Alterado status da doação com id {} pendente", doacao.getId());
        doacao.setStatus(Status.PENDENTE);

        log.info("Alterado atributos do match com id {} referente a sua recusa", match.getId());
        match.setMotivoReprovacao(request.getMotivoReprovacao());
        match.setDataTermino(LocalDateTime.now());
        match.setFinalizadoPor(userPrincipal.getEmail());
        match.setStatus(Status.RECUSADA);

        log.info("Salvando no banco o match");
        matchRepository.save(match);
    }
}


