package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class CadastrarUsuarioRequestValidator {

    private final EmailValidator emailValidator;
    private final CpfCnpjValidator cpfCnpjValidator;

    public void validar(final CadastrarUsuarioRequest request) {

        if (isNull(request)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getCep())) {
            throw new ApiException(Mensagem.CEP_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getNome())) {
            throw new ApiException(Mensagem.NOME_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        emailValidator.validar(request.getEmail());

        if (isEmpty(request.getSenha())) {
            throw new ApiException(Mensagem.SENHA_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        cpfCnpjValidator.validar(request.getDocumento());

        if (isEmpty(request.getTelefone())) {
            throw new ApiException(Mensagem.TELEFONE_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getEstado())) {
            throw new ApiException(Mensagem.ESTADO_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getCidade())) {
            throw new ApiException(Mensagem.CIDADE_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getBairro())) {
            throw new ApiException(Mensagem.BAIRRO_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getRua())) {
            throw new ApiException(Mensagem.RUA_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getNumero())) {
            throw new ApiException(Mensagem.NUMERO_CASA_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

    }
}