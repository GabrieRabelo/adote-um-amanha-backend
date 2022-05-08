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

    private String estado;
    private String cidade;
    private String bairro;
    private String CEP;
    private String rua;
    private String numero;
    private String complemento;

}


