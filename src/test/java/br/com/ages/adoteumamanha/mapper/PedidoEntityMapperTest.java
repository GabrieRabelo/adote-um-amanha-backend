package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.domain.entity.UsuarioEntity;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class PedidoEntityMapperTest {

    final PedidoEntityMapper MAPPER = new PedidoEntityMapper();

    private UserPrincipal userPrincipal;
    private CadastrarPedidoRequest request;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(UsuarioEntity.builder()).build());
    }

    @Test
    public void mapper() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();

       final PedidoEntity entity = MAPPER.apply(request, userPrincipal);

        Assert.assertEquals(request.getAssunto(), entity.getAssunto());
        Assert.assertEquals(request.getDescricao(), entity.getDescricao());
        Assert.assertEquals(request.getCategoria(), entity.getCategoria());
        Assert.assertEquals(request.getSubcategoria(), entity.getSubcategoria());
        Assert.assertEquals(LocalDate.now().getYear(), entity.getDataHora().getYear());
        Assert.assertEquals(LocalDate.now().getMonth(), entity.getDataHora().getMonth());
        Assert.assertEquals(LocalDate.now().getDayOfWeek(), entity.getDataHora().getDayOfWeek());
        Assert.assertEquals(userPrincipal.getId(), entity.getUsuario().getId());
    }

}