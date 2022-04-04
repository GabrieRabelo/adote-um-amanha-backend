package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.PedidoEntityMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoEntityMapper mapper;

    @Mock
    private CadastrarPedidoRequestValidator validator;

    private CadastrarPedidoRequest request;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<PedidoEntity> pedidoEntityArgumentCaptor;


    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder()).build());
    }

    @Test
    public void ok() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();

        when(mapper.apply(request, userPrincipal)).thenCallRealMethod();

        service.cadastrar(request, userPrincipal);

        verify(validator).validate(request);
        verify(mapper).apply(request, userPrincipal);

        verify(repository).save(pedidoEntityArgumentCaptor.capture());

        final PedidoEntity entity = pedidoEntityArgumentCaptor.getValue();

        Assert.assertEquals(request.getAssunto(), entity.getAssunto());
        Assert.assertEquals(request.getDescricao(), entity.getDescricao());
        Assert.assertEquals(request.getCategoria(), entity.getCategoria());
        Assert.assertEquals(request.getSubcategoria(), entity.getSubcategoria());
        Assert.assertEquals(LocalDate.now().getYear(), entity.getDataHora().getYear());
        Assert.assertEquals(LocalDate.now().getMonth(), entity.getDataHora().getMonth());
        Assert.assertEquals(LocalDate.now().getDayOfWeek(), entity.getDataHora().getDayOfWeek());
        Assert.assertEquals(userPrincipal.getId(), entity.getUsuario().getId());

    }

    @Test(expected = ApiException.class)
    public void validatorErro() {

        doThrow(ApiException.class).when(validator).validate(request);

        service.cadastrar(request, userPrincipal);

        verifyNoInteractions(mapper, repository);
    }
}