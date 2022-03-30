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
@Entity(name = "usuario")
@Builder(setterPrefix = "with")
public class UsuarioEntity implements Serializable {

    public static final String SEQUENCE_NAME = "usuario_sequence";
    public static final String SEQUENCE_ID = "USUARIO_ID_SEQ";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_ID, allocationSize = 1)
    @Column(nullable = false)
    private Long idUsuario;

    private Boolean ativo;

    private String nome;

    private String email;

    @JsonIgnore
    private String senha;

    private String documento;

    private String telefone;

    private String site;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

}

