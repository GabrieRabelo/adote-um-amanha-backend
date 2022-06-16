package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
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
public class DescricaoPedidoResponse implements Serializable {

    private static final long serialVersionUID = 1727499558462616832L;

    private final Long id;
    private final Long idUsuario;
    private final String urlVideo;
    private final String nomeUsuario;
    private final String assunto;
    private final String descricao;
    private final Categoria categoria;
    private final Subcategoria subcategoria;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime data;

    private final Status status;
    private final TipoPedido tipo;
    private final String motivoRecusa;

}