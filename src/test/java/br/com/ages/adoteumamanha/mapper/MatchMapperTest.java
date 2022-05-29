package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.*;

public class MatchMapperTest {

    final MatchMapper MAPPER = new MatchMapper();

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void mapper_user_null() {

        final Pedido necessidade = make(Pedido.builder()).build();
        final Pedido doacao = make(Pedido.builder()).build();

        final Match response = MAPPER.apply(null, doacao, necessidade);
        assertNull(response);
    }

    @Test
    public void mapper_doacao_null() {

        final Pedido necessidade = make(Pedido.builder()).build();

        final Match response = MAPPER.apply(userPrincipal, null, necessidade);
        assertNull(response);
    }

    @Test
    public void mapper_necessidade_null() {

        final Pedido doacao = make(Pedido.builder()).build();

        final Match response = MAPPER.apply(userPrincipal, doacao, null);
        assertNull(response);
    }

    @Test
    public void mapper() {

        final Pedido necessidade = make(Pedido.builder()).build();
        final Pedido doacao = make(Pedido.builder()).build();

        final Match response = MAPPER.apply(userPrincipal, doacao, necessidade);

        assertEquals(doacao, response.getDoacao());
        assertEquals(necessidade, response.getNecessidade());
        assertNotNull(response.getDataCriacao());
        assertNotNull(response.getDescricao());
        assertNotNull(response.getStatus());
    }
}