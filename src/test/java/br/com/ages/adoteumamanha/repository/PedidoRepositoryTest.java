package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.SERVIÇO;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.SAUDE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
    }

    @Test
    void getByTipoPedido() {
        //Arrange

        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero(1)
                .build();

        Usuario usuario = Usuario.builder()
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

        Pedido pedidoParaSalvar = Pedido.builder()
                .withTipoPedido(NECESSIDADE)
                .withCategoria(SERVIÇO)
                .withSubcategoria(SAUDE)
                .withStatus(PENDENTE)
                .withAssunto("Assunto")
                .withDescricao("Descrição")
                .withDataHora(LocalDateTime.of(2022, Month.APRIL, 20, 12, 30, 40))
                .withUsuario(usuario)
                .build();

        pedidoRepository.save(pedidoParaSalvar);

        Pageable paging = PageRequest.of(0, 5, by(Sort.Direction.valueOf(DESC.name()), "dataHora"));

        //Act
        Page<Pedido> pedidoPages = pedidoRepository.findAllByTipoPedido(NECESSIDADE, paging);
        Optional<Pedido> pedidoOpt = pedidoPages.stream().findFirst();
        //Assert
        assertThat(pedidoOpt).isPresent();
        var pedido = pedidoOpt.get();

        assertThat(pedido.getTipoPedido()).isEqualTo(pedidoParaSalvar.getTipoPedido());
    }

    @Test
    void findAllByTipoPedidoAndStatus() {
        //Arrange

        Endereco endereco = Endereco.builder()
                .withBairro("Partenon")
                .withCEP("91530034")
                .withComplemento("")
                .withNumero(1)
                .build();

        Usuario usuario = Usuario.builder()
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

        Pedido pedidoParaSalvar = Pedido.builder()
                .withTipoPedido(NECESSIDADE)
                .withCategoria(SERVIÇO)
                .withSubcategoria(SAUDE)
                .withStatus(PENDENTE)
                .withAssunto("Assunto")
                .withDescricao("Descrição")
                .withDataHora(LocalDateTime.of(2022, Month.APRIL, 20, 12, 30, 40))
                .withUsuario(usuario)
                .build();

        pedidoRepository.save(pedidoParaSalvar);

        Pageable paging = PageRequest.of(0, 5, by(Sort.Direction.valueOf(DESC.name()), "dataHora"));

        //Act
        Page<Pedido> pedidoPages = pedidoRepository.findAllByTipoPedidoAndStatus(NECESSIDADE, PENDENTE, paging);
        Optional<Pedido> pedidoOpt = pedidoPages.stream().findFirst();
        //Assert
        assertThat(pedidoOpt).isPresent();
        var pedido = pedidoOpt.get();

        assertThat(pedido.getTipoPedido()).isEqualTo(pedidoParaSalvar.getTipoPedido());
        assertThat(pedido.getStatus()).isEqualTo(pedidoParaSalvar.getStatus());
    }
}