package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder(setterPrefix = "with")
public class ResumoPedidoResponse implements Serializable {

    private static final long serialVersionUID = -700688231746616781L;

    private Long id;
    private String assunto;
    private Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime data;
    private Status status;
    private TipoPedido tipo;

}
