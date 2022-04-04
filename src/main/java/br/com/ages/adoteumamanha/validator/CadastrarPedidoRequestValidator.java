package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class CadastrarPedidoRequestValidator {

    public void validate(final CadastrarPedidoRequest request) {

        if (isNull(request)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getAssunto())) {
            throw new ApiException(Mensagem.ASSUNTO_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getDescricao())) {
            throw new ApiException(Mensagem.DESCRICAO_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getCategoria())) {
            throw new ApiException(Mensagem.CATEGORIA_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getSubcategoria())) {
            throw new ApiException(Mensagem.SUBCATEGORIA_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }
}
