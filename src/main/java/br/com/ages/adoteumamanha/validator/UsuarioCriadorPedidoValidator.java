package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
@RequiredArgsConstructor
public class UsuarioCriadorPedidoValidator {

    public void validate(final Long idUsuarioLogado, final Long idUsuarioCriadorPedido) {
        if (isFalse(idUsuarioLogado.equals(idUsuarioCriadorPedido))) {
            throw new ApiException(Mensagem.PEDIDO_NAO_PODE_SER_DELETADO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
