package br.com.ages.adoteumamanha.domain.entity;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Table
@Entity
public class Match implements Serializable {

    public static final String SEQUENCE_NAME = "pedido_sequence";
    public static final String SEQUENCE_ID = "PEDIDO_ID_SEQ";

    @Id
    @Column(nullable = false)
    private Long idDoador;

    @Column(nullable = false)
    private Long idNecessidade;

    @Column(nullable = false)
    private LocalDateTime data;

    private String descricao;

}


