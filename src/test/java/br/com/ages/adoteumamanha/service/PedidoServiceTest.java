package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.NecessidadeResponseMapper;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.ASC;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;
import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.by;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private NecessidadesResponseMapper necessidadesResponseMapper;

    @Mock
    private NecessidadeResponseMapper necessidadeResponseMapper;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private CadastrarPedidoRequestValidator validator;

    private CadastrarPedidoRequest request;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Pedido> pedidoEntityArgumentCaptor;


    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder()).build());
    }

    @Test
    public void cadastrar_ok() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();
        when(pedidoMapper.apply(request, userPrincipal)).thenCallRealMethod();

        service.cadastrar(request, userPrincipal);

        verify(validator).validate(request);
        verify(pedidoMapper).apply(request, userPrincipal);
        verify(repository).save(pedidoEntityArgumentCaptor.capture());

        final Pedido entity = pedidoEntityArgumentCaptor.getValue();

        assertEquals(request.getAssunto(), entity.getAssunto());
        assertEquals(request.getDescricao(), entity.getDescricao());
        assertEquals(request.getCategoria(), entity.getCategoria());
        assertEquals(request.getSubcategoria(), entity.getSubcategoria());
        assertEquals(LocalDate.now().getYear(), entity.getDataHora().getYear());
        assertEquals(LocalDate.now().getMonth(), entity.getDataHora().getMonth());
        assertEquals(LocalDate.now().getDayOfWeek(), entity.getDataHora().getDayOfWeek());
        assertEquals(userPrincipal.getId(), entity.getUsuario().getId());

    }

    @Test(expected = ApiException.class)
    public void cadastrar_validator_erro() {

        doThrow(ApiException.class).when(validator).validate(request);

        service.cadastrar(request, userPrincipal);

        verifyNoInteractions(pedidoMapper, repository);
    }

    @Test
    public void listar_necessidade_ok() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";
        final String status = "ANY";

        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));
        final Page<Pedido> pedidoEntityPage = mock(Page.class);

        when(repository.findAllByTipoPedido(TipoPedido.NECESSIDADE, paging)).thenReturn(pedidoEntityPage);
        service.listarNecessidades(pagina, tamanho, ordenacao, direcao, status);

        verify(necessidadesResponseMapper).apply(pedidoEntityPage);
        verify(repository).findAllByTipoPedido(eq(TipoPedido.NECESSIDADE), eq(paging));
    }

    @Test
    public void descricao_necessidade_ok() {

        final Long id = 1L;
        final Pedido pedido = Fixture.make(Pedido.builder()).build();

        when(repository.findById(id)).thenReturn(of(pedido));

        service.descricaoNecessidade(id);

        verify(necessidadeResponseMapper).apply(pedido);
        verify(repository).findById(eq(id));
    }

    @Test(expected = ApiException.class)
    public void descricao_necessidade_nao_encontrada_erro() {

        final Long id = 1L;

        when(repository.findById(id)).thenReturn(empty());

        service.descricaoNecessidade(id);

        verifyNoInteractions(necessidadeResponseMapper);
    }

    @Test
    public void deletar_pedido_ok() {

        final Long idNecessidade = 1L;
        final Long idUsuario = userPrincipal.getId();

        final Usuario usuario = Fixture.make(Usuario.builder())
                .withId(idUsuario)
                .build();

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withStatus(Status.PENDENTE)
                .withUsuario(usuario)
                .build();

        when(repository.findById(idNecessidade)).thenReturn(of(pedido));

        service.deletarPedido(idNecessidade, userPrincipal);

        verify(repository).findById(eq(idNecessidade));
        verify(repository).delete(eq(pedido));
    }

    @Test(expected = ApiException.class)
    public void deletar_pedido_nao_encontrado_erro() {

        final Long idNecessidade = 1L;

        doThrow(ApiException.class).when(repository).findById(idNecessidade);

        service.deletarPedido(idNecessidade, userPrincipal);

        verifyNoInteractions(repository);
    }

    @Test(expected = ApiException.class)
    public void deletar_pedido_status_nao_pendente_erro() {

        final Long idNecessidade = 1L;

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withStatus(Status.FINALIZADA)
                .build();

        when(repository.findById(idNecessidade)).thenReturn(of(pedido));

        service.deletarPedido(idNecessidade, userPrincipal);

        verify(repository).findById(eq(idNecessidade));
        verifyNoMoreInteractions(repository);
    }


    @Test(expected = ApiException.class)
    public void deletar_pedido_nao_pertence_usuario_erro() {

        final Long idNecessidade = 1L;

        final Usuario usuario = Fixture.make(Usuario.builder())
                .withId(999L)
                .build();

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withStatus(Status.PENDENTE)
                .withUsuario(usuario)
                .build();

        when(repository.findById(idNecessidade)).thenReturn(ofNullable(pedido));

        service.deletarPedido(idNecessidade, userPrincipal);

        verifyNoInteractions(necessidadeResponseMapper);
    }

    @Test
    public void atualizar_pedido_ok() {

        final Long idNecessidade = 1L;
        final Long idUsuario = userPrincipal.getId();

        final AtualizarPedidoRequest request = Fixture.make(AtualizarPedidoRequest.builder().build());

        final Usuario usuario = Fixture.make(Usuario.builder())
                .withId(idUsuario)
                .build();

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withStatus(Status.PENDENTE)
                .withUsuario(usuario)
                .build();

        final Pedido pedidoAtualizado = pedido;
        pedido.setAssunto(request.getAssunto());
        pedido.setDescricao(request.getDescricao());
        pedido.setCategoria(request.getCategoria());
        pedido.setSubcategoria(request.getSubcategoria());
        pedido.setUrlVideo(request.getUrlVideo());

        when(repository.findById(idNecessidade)).thenReturn(of(pedido));
        when(repository.save(any())).thenReturn(pedidoAtualizado);
        when(necessidadeResponseMapper.apply(any())).thenCallRealMethod();

        final NecessidadeResponse resultado = service.atualizarPedido(idNecessidade, request, userPrincipal);

        verify(repository).findById(eq(idNecessidade));
        verify(repository).save(pedidoEntityArgumentCaptor.capture());
        verify(necessidadeResponseMapper).apply(pedidoEntityArgumentCaptor.getValue());

        assertEquals(resultado.getAssunto(), pedidoEntityArgumentCaptor.getValue().getAssunto());
        assertEquals(resultado.getDescricao(), pedidoEntityArgumentCaptor.getValue().getDescricao());
        assertEquals(resultado.getSubcategoria(), pedidoEntityArgumentCaptor.getValue().getSubcategoria());
        assertEquals(resultado.getCategoria(), pedidoEntityArgumentCaptor.getValue().getCategoria());
        assertEquals(resultado.getUrlVideo(), pedidoEntityArgumentCaptor.getValue().getUrlVideo());
    }

    @Test(expected = ApiException.class)
    public void atualizar_pedido_request_invalido() {

        final Long idNecessidade = 1L;

        service.atualizarPedido(idNecessidade, null, userPrincipal);

        verifyNoInteractions(repository, necessidadeResponseMapper);
    }

    @Test(expected = ApiException.class)
    public void atualizar_pedido_nao_encontrado_erro() {

        final Long idNecessidade = 1L;
        final AtualizarPedidoRequest request = Fixture.make(AtualizarPedidoRequest.builder().build());

        doThrow(ApiException.class).when(repository).findById(idNecessidade);

        service.atualizarPedido(idNecessidade, request, userPrincipal);

        verifyNoMoreInteractions(repository);
        verifyNoInteractions(necessidadeResponseMapper);
    }

    @Test(expected = ApiException.class)
    public void atualizar_pedido_status_nao_pendente_erro() {

        final Long idNecessidade = 1L;
        final AtualizarPedidoRequest request = Fixture.make(AtualizarPedidoRequest.builder().build());

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withStatus(Status.FINALIZADA)
                .build();

        when(repository.findById(idNecessidade)).thenReturn(of(pedido));

        service.atualizarPedido(idNecessidade, request, userPrincipal);

        verify(repository).findById(eq(idNecessidade));
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(necessidadeResponseMapper);
    }


    @Test(expected = ApiException.class)
    public void atualizar_pedido_nao_pertence_usuario_erro() {

        final Long idNecessidade = 1L;
        final AtualizarPedidoRequest request = Fixture.make(AtualizarPedidoRequest.builder().build());

        final Usuario usuario = Fixture.make(Usuario.builder())
                .withId(999L)
                .build();

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withStatus(Status.PENDENTE)
                .withUsuario(usuario)
                .build();

        when(repository.findById(idNecessidade)).thenReturn(ofNullable(pedido));

        service.atualizarPedido(idNecessidade, request, userPrincipal);

        verifyNoMoreInteractions(repository);
        verifyNoInteractions(necessidadeResponseMapper);
    }

}
