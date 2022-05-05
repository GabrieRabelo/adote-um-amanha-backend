package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.SERVIÇO;
import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.DESC;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.SAUDE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        matchRepository.deleteAll();
    }

    @Test
    void findAllById() {
        //Arrange

        Usuario casa = Usuario.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(null)
                .withPerfil(Perfil.CASA)
                .withNome("rabelo")
                .withSite("rabelo.com")
                .withTelefone("123123123")
                .withSenha("password")
                .build();

        Usuario usuario = Usuario.builder()
                .withEmail("rabelo@rab.elo")
                .withAtivo(true)
                .withDocumento("12312312312")
                .withEndereco(null)
                .withPerfil(Perfil.DOADOR)
                .withNome("rabelo")
                .withSite("rabelo.com")
                .withTelefone("123123123")
                .withSenha("password")
                .build();

        Pedido necessidade = Pedido.builder()
                .withTipoPedido(NECESSIDADE)
                .withCategoria(SERVIÇO)
                .withSubcategoria(SAUDE)
                .withStatus(PENDENTE)
                .withAssunto("Assunto")
                .withDescricao("Descrição")
                .withDataHora(LocalDateTime.of(2022, Month.APRIL, 20, 12, 30, 40))
                .withUsuario(casa)
                .build();

        Pedido doacao = Pedido.builder()
                .withTipoPedido(DOACAO)
                .withCategoria(SERVIÇO)
                .withSubcategoria(SAUDE)
                .withStatus(PENDENTE)
                .withAssunto("Assunto")
                .withDescricao("Descrição")
                .withDataHora(LocalDateTime.of(2022, Month.APRIL, 20, 12, 30, 40))
                .withUsuario(usuario)
                .build();

        Match matchParaSalvar = Match.builder()
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .withData(LocalDateTime.MAX)
                .withStatus(PENDENTE)
                .withId(1L)
                .build();

        matchRepository.save(matchParaSalvar);

        Pageable paging = PageRequest.of(0, 5, by(Sort.Direction.valueOf(DESC.name()), "dataHora"));

        //Act
        Page<Match> matchPages = matchRepository.findAllById(1L);
        Optional<Match> matchOpt = matchPages.stream().findFirst();
        //Assert
        assertThat(matchOpt).isPresent();
        var match = matchOpt.get();

        assertThat(match.getId()).isEqualTo(matchParaSalvar.getId());
        assertThat(match.getDoacao().getId()).isEqualTo(matchParaSalvar.getDoacao().getId());
        assertThat(match.getNecessidade().getId()).isEqualTo(matchParaSalvar.getNecessidade().getId());
    }

}