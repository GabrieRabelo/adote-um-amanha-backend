package br.com.ages.adoteumamanha.domain.entity;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@Builder(setterPrefix = "with")
@Entity
@Table
public class Usuario implements Serializable {

    public static final String SEQUENCE_NAME = "usuario_sequence";
    public static final String SEQUENCE_ID = "USUARIO_ID_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_ID, allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String site;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "estado", column = @Column(name = "endereco_estado")),
            @AttributeOverride(name = "cidade", column = @Column(name = "endereco_cidade")),
            @AttributeOverride(name = "bairro", column = @Column(name = "endereco_bairro")),
            @AttributeOverride(name = "CEP", column = @Column(name = "endereco_CEP")),
            @AttributeOverride(name = "rua", column = @Column(name = "endereco_rua")),
            @AttributeOverride(name = "numero", column = @Column(name = "endereco_numero")),
            @AttributeOverride(name = "complemento", column = @Column(name = "endereco_complemento"))
    })

    @Column(nullable = false)
    private Endereco endereco;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

}

