package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class MatchValidator {

    public void validate(final Match match) {

        if (isNull(match)) {
            throw new ApiException(Mensagem.MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isNull(match.getDoacao()) || isFalse(DOACAO.equals(match.getDoacao().getTipoPedido()))) {
            throw new ApiException(Mensagem.DOACAO_MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isNull(match.getNecessidade()) || isFalse(NECESSIDADE.equals(match.getNecessidade().getTipoPedido()))) {
            throw new ApiException(Mensagem.NECESSIDADE_MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

    }
}


