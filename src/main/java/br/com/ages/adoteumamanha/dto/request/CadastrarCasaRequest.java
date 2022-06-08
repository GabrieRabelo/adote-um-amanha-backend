package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder(setterPrefix = "with")
public class CadastrarCasaRequest {


        private static final long serialVersionUID = 831463528928569077L;

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
