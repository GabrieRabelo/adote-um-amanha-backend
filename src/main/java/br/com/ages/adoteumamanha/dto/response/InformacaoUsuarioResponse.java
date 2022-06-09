package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public class InformacaoUsuarioResponse implements Serializable {

    private static final long serialVersionUID = -2338010642651294681L;

    private final String nome;
    private Boolean ativo;
    private String email;
    private String documento;
    private String telefone;
    private final Perfil perfil;
    private Endereco endereco;
    private int doacoesAprovadas;
    private int doacoesRecusadas;

}