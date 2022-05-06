package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public class UsuarioResponse implements Serializable {

    private static final long serialVersionUID = -2338010642651294681L;

    private String nome;
    private Perfil perfil;

}
