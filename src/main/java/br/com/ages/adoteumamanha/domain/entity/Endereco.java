package br.com.ages.adoteumamanha.domain.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Embeddable
public class Endereco implements Serializable {

    private String rua;

    private String bairro;

    private String cidade;

    private String estado;

    private Integer numero;

    private String complemento;

    private String CEP;
}


