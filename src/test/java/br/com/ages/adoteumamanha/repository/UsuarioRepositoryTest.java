package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.UsuarioEntity;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void getByEmail() {
        //Arrange
        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero(1)
                .build();

        UsuarioEntity usuarioParaSalvar = UsuarioEntity.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(endereco)
                .build();

        usuarioRepository.save(usuarioParaSalvar);

        //Act
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByEmail("rabelo@rab.elo");

        //Assert
        assertThat(usuarioOpt).isPresent();
        var usuario = usuarioOpt.get();

        assertThat(usuario.getEmail()).isEqualTo(usuarioParaSalvar.getEmail());
    }
}