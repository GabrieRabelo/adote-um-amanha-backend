package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
public final class CadastrarUsuarioRequest implements Serializable {

    private static final long serialVersionUID = -36932922272201950L;

    // dados pessoais do usuário
    private final String nome;
    private final String email;
    private final String senha;
    private final String documento;
    private final String telefone;
    private final String site;

    // endereço do usuário
    private final String estado;
    private final String cidade;
    private final String bairro;
    private final String cep;
    private final String rua;
    private final Integer numero;
    private final String complemento;

}