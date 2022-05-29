package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder(setterPrefix = "with")
public class ResumoMatchResponse implements Serializable {

    private static final long serialVersionUID = -700688231746616781L;

    private final Long id;
    private final String nomeCasa;
    private final String nomeDoador;

    private final String assunto;

    private final Categoria categoria;
    private final Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime data;

    private final Status status;
}
