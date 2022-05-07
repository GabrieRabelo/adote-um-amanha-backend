package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
@RequiredArgsConstructor
public class VinculacaoDoacaoNecessidadeMatchValidator {

    public void validate(final TipoPedido tipoPedido) {
        if (isFalse(NECESSIDADE.equals(tipoPedido))) {
            throw new ApiException(Mensagem.VINCULACAO_DOACAO_DOACAO_MATCH.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
