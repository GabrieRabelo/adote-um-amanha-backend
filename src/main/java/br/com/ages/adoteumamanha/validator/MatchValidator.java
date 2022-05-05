package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class MatchValidator {

    public void validate(final Match match) {

        if (isNull(match)) {
            throw new ApiException(Mensagem.MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isNull(match.getDoacao()) || DOACAO.equals(match.getNecessidade().getTipoPedido())) {
            throw new ApiException(Mensagem.DOACAO_MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isNull(match.getNecessidade()) || NECESSIDADE.equals(match.getNecessidade().getTipoPedido())) {
            throw new ApiException(Mensagem.NECESSIDADE_MATCH_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

    }
}


