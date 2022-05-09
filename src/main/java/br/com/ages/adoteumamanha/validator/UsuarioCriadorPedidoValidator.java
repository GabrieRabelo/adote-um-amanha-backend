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

    public void validar(final Long idUsuarioLogado, final Long idUsuarioCriadorPedido) {
        if (isFalse(idUsuarioLogado.equals(idUsuarioCriadorPedido))) {
            throw new ApiException(Mensagem.USUARIO_NAO_PODE_DELETAR_PEDIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
