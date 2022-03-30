package br.com.ages.adoteumamanha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Endereco {

    private String rua;

    private String bairro;

    private Integer numero;

    private String complemento;

    private String CEP;
}


