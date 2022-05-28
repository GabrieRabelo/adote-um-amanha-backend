package br.com.ages.adoteumamanha.domain.entity;

import br.com.ages.adoteumamanha.domain.enumeration.Status;
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

    public static final String SEQUENCE_NAME = "match_sequence";
    public static final String SEQUENCE_ID = "MATCH_ID_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_ID, allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Pedido necessidade;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Pedido doacao;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataTermino;

    private String finalizadoPor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String descricao;

    private String motivoReprovacao;

}


