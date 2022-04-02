package br.com.ages.adoteumamanha.domain.entity;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@Table(name = "usuario")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class UsuarioEntity implements Serializable {

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
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

}

