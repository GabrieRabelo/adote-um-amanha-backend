package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
public final class NecessidadeResponse implements Serializable {

    private static final long serialVersionUID = -877582952797455097L;

    private final Long id;
    private final String assunto;
    private final Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private final LocalDate data;

    private final Status status;

}


