package br.com.ages.adoteumamanha.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with", toBuilder = true)
@Table
@Entity
public class ResetarSenha implements Serializable {

    public static final String SEQUENCE_NAME = "resetar_senha_sequence";
    public static final String SEQUENCE_ID = "RESETAR_SENHA_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_ID, allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column
    private Boolean usado;

    @PrePersist
    private void setUsado() {
        usado = FALSE;
    }
}


