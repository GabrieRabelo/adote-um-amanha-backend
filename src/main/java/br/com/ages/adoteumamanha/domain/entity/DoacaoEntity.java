package br.com.ages.adoteumamanha.domain.entity;

import br.com.ages.adoteumamanha.domain.enumeration.CategoriaEnum;
import br.com.ages.adoteumamanha.domain.enumeration.DonationEnum;
import br.com.ages.adoteumamanha.domain.enumeration.RoleEnum;
import br.com.ages.adoteumamanha.domain.enumeration.SubCategoriaEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "donation")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class DoacaoEntity implements Serializable {

    public static final String SEQUENCE_NAME = "donation_sequence";
    public static final String SEQUENCE_ID = "DONATION_ID_SEQ";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_ID, allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    //TODO verificar o banco e add o JoinColumn com o nome da fk criada aqui
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioEntity solicitante;

    @Setter
    private LocalDateTime dataHora;

    private String assunto;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private DonationEnum tipoDoacao;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;

    @Enumerated(EnumType.STRING)
    private SubCategoriaEnum subCategoria;

    @PrePersist
    public void atribuiDataHora() {
        this.dataHora = LocalDateTime.now();
    }
}

