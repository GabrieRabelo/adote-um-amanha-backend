package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReprovarMatchService {

    private final MatchRepository matchRepository;

    public void reprovar(Long idMatch, UserPrincipal userPrincipal) {

        final Match match = matchRepository.findById(idMatch)
                .orElseThrow(() -> new ApiException(Mensagem.MATCH_INEXISTENTE.getDescricao(), HttpStatus.NOT_FOUND));

        final Pedido necessidade = match.getNecessidade();
        final Pedido doacao = match.getDoacao();

        if(Status.FINALIZADA.equals(necessidade.getStatus()) && Status.FINALIZADA.equals(doacao.getStatus())){
            throw new ApiException(Mensagem.MATCH_FINALIZADO.getDescricao(), HttpStatus.NOT_FOUND);
        }

        necessidade.setStatus(Status.FINALIZADA);
        doacao.setStatus(Status.FINALIZADA);
        match.setStatus(Status.FINALIZADA);

        matchRepository.save(match);

    }
}


