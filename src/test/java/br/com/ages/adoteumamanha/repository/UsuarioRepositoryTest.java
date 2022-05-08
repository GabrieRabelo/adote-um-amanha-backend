package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    void getByEmail() {
        //Arrange
        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero("1")
                .build();

        Usuario usuarioParaSalvar = Usuario.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(endereco)
                .withPerfil(Perfil.CASA)
                .withNome("rabelo")
                .withSite("rabelo.com")
                .withTelefone("123123123")
                .withSenha("password")
                .build();

        usuarioRepository.save(usuarioParaSalvar);

        //Act
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail("rabelo@rab.elo");

        //Assert
        assertThat(usuarioOpt).isPresent();
        var usuario = usuarioOpt.get();

        assertThat(usuario.getEmail()).isEqualTo(usuarioParaSalvar.getEmail());
    }

    @Test
    void getByIdAndPerfil() {
        //Arrange
        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero("1")
                .build();

        Usuario usuarioParaSalvar = Usuario.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(endereco)
                .withPerfil(Perfil.CASA)
                .withNome("rabelo")
                .withSite("rabelo.com")
                .withTelefone("123123123")
                .withSenha("password")
                .build();

        usuarioRepository.save(usuarioParaSalvar);

        //Act
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdAndPerfil(usuarioParaSalvar.getId(), Perfil.CASA);

        //Assert
        assertThat(usuarioOpt).isPresent();
        var usuario = usuarioOpt.get();

        assertThat(usuario.getEmail()).isEqualTo(usuarioParaSalvar.getEmail());
    }

    @Test
    void getByIdAndPerfil_vazio() {
        //Arrange
        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero("1")
                .build();

        Usuario usuarioParaSalvar = Usuario.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(endereco)
                .withPerfil(Perfil.CASA)
                .withNome("rabelo")
                .withSite("rabelo.com")
                .withTelefone("123123123")
                .withSenha("password")
                .build();

        usuarioRepository.save(usuarioParaSalvar);

        //Act
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdAndPerfil(usuarioParaSalvar.getId(), Perfil.ADMIN);

        //Assert
        assertThat(usuarioOpt).isEmpty();

    }
}