package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with")
public class PedidoCurtoResponse implements Serializable {

    private static final long serialVersionUID = 887764224973488730L;

    private Long id;
    private String assunto;
    private Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime data;
    private Status status;

}
