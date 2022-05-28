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
    private final Long idCasa;
    private final Long idDoador;
    private Long id;
    private String assunto;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime data;
    private Categoria categoria;
    private Subcategoria subcategoria;
    private Status status;
    private String descricaoDoacao;
    private String nomeCasa;
    private String nomeDoador;
}
