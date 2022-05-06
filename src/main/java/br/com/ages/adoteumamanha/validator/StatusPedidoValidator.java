package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
@RequiredArgsConstructor
public class StatusPedidoValidator {

    public void validate(final Pedido pedido) {
        if (isFalse(PENDENTE.equals(pedido.getStatus()))) {
            throw new ApiException(Mensagem.STATUS_NAO_PENDENTE.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
