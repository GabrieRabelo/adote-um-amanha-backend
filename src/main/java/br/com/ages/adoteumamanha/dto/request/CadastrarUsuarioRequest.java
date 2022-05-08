package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
public final class CadastrarUsuarioRequest implements Serializable {

    private static final long serialVersionUID = 9108505036289116509L;

    private final String nome;

    private final String documento;

    private final String cep;

    private final String bairro;

    private final String rua;

    private final String numero;

    private final String complemento;

    private final String email;

    private final String telefone;

    private final String senha;

    private final String estado;

    private final String cidade;

}
