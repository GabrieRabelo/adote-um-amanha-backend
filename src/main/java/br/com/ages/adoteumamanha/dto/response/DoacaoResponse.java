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
public class DoacaoResponse implements Serializable {

    private static final long serialVersionUID = 8877642249734887304L;

    private Long id;
    private String assunto;
    private String descricao;
    private Categoria categoria;
    private Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime data;
    private Status status;

}
