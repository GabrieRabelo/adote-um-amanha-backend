package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder(setterPrefix = "with")
public class DescricaoPedidoResponse implements Serializable {

    private static final long serialVersionUID = 1727499558462616832L;

    private Long id;
    private String assunto;
    private String descricao;
    private Categoria categoria;
    private Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime data;
    private Status status;
    private TipoPedido tipo;
    private final String urlVideo;

    private final Long idUsuario;
    private final String nomeUsuario;

}