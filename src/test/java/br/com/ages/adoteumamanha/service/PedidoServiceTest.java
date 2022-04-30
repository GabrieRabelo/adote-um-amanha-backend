package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.DoacaoResponse;
import br.com.ages.adoteumamanha.dto.response.NecessidadeResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.DoacaoResponseMapper;
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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.ASC;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    private DoacaoResponseMapper doacaoResponseMapper;

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
    public void listar_necessidade_sem_filtros() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";

        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));
        final Page<Pedido> pedidoEntityPage = mock(Page.class);

        when(repository.findAllByTipoPedido(TipoPedido.NECESSIDADE, paging)).thenReturn(pedidoEntityPage);
        service.listarNecessidades(pagina, tamanho, ordenacao, direcao, null);

        verify(necessidadesResponseMapper).apply(pedidoEntityPage);
        verify(repository).findAllByTipoPedido(eq(TipoPedido.NECESSIDADE), eq(paging));
    }

    @Test
    public void listar_necessidade_apenas_pendentes() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";
        final Status status = PENDENTE;

        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));
        final Page<Pedido> pedidoEntityPage = mock(Page.class);

        when(repository.findAllByTipoPedidoAndStatus(TipoPedido.NECESSIDADE, status, paging)).thenReturn(pedidoEntityPage);
        service.listarNecessidades(pagina, tamanho, ordenacao, direcao, status);

        verify(necessidadesResponseMapper).apply(pedidoEntityPage);
        verify(repository).findAllByTipoPedidoAndStatus(eq(TipoPedido.NECESSIDADE), eq(PENDENTE), eq(paging));
    }

    @Test
    public void descricao_necessidade_ok() {

        final Long id = 1L;
        final Pedido pedido = Fixture.make(Pedido.builder()).build();

        when(repository.findByIdAndTipoPedido(id, TipoPedido.NECESSIDADE)).thenReturn(of(pedido));

        service.descricaoNecessidade(id);

        verify(necessidadeResponseMapper).apply(pedido);
        verify(repository).findByIdAndTipoPedido(any(), any());
    }

    @Test
    public void descricao_doacao_ok() {

        final Long id = 1L;
        final Pedido pedido = Fixture.make(Pedido.builder()).build();
        final Usuario usuario = Fixture.make(Usuario.builder()).build();
        pedido.setUsuario(usuario);

        when(repository.findByIdAndTipoPedido(id, TipoPedido.DOACAO)).thenReturn(of(pedido));

        service.descricaoDoacao(id, pedido.getUsuario().getId());

        verify(doacaoResponseMapper).apply(pedido);
        verify(repository).findByIdAndTipoPedido(any(), any());
    }

    @Test
    public void descricao_doacao_nao_encontrada_erro() {

        final Long id = 1L;

        when(repository.findByIdAndTipoPedido(id, TipoPedido.DOACAO)).thenReturn(empty());

        assertThatCode(() -> service.descricaoDoacao(id, id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Doação não encontrada");

        verify(repository).findByIdAndTipoPedido(any(), any());
        verifyNoInteractions(doacaoResponseMapper);
    }

    @Test
    public void descricao_doacao_nao_autorizado_erro() {

        final Long id = 1L;
        final Pedido pedido = Fixture.make(Pedido.builder()).build();
        final Usuario usuario = Fixture.make(Usuario.builder()).build();
        pedido.setUsuario(usuario);

        when(repository.findByIdAndTipoPedido(id, TipoPedido.DOACAO)).thenReturn(of(pedido));

        assertThatCode(() -> service.descricaoDoacao(id, id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Doação não pertence ao usuário conectado");

        verify(repository).findByIdAndTipoPedido(any(), any());
        verifyNoInteractions(doacaoResponseMapper);
    }

    @Test(expected = ApiException.class)
    public void descricao_necessidade_nao_encontrada_erro() {

        final Long id = 1L;

        when(repository.findByIdAndTipoPedido(id, TipoPedido.NECESSIDADE)).thenReturn(empty());

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

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(of(pedido));

        service.deletarPedido(idNecessidade, userPrincipal);

        verify(repository).findByIdAndTipoPedido(any(), any());
        verify(repository).delete(any());
    }

    @Test(expected = ApiException.class)
    public void deletar_pedido_nao_encontrado_erro() {

        final Long idNecessidade = 1L;

        doThrow(ApiException.class).when(repository).findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE);

        service.deletarPedido(idNecessidade, userPrincipal);

        verifyNoInteractions(repository);
    }

    @Test(expected = ApiException.class)
    public void deletar_pedido_status_nao_pendente_erro() {

        final Long idNecessidade = 1L;

        final Pedido pedido = Fixture.make(Pedido.builder())
                .withStatus(Status.FINALIZADA)
                .build();

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(of(pedido));

        service.deletarPedido(idNecessidade, userPrincipal);

        verify(repository).findByIdAndTipoPedido(any(), any());
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

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(ofNullable(pedido));

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

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(of(pedido));
        when(repository.save(any())).thenReturn(pedidoAtualizado);
        when(necessidadeResponseMapper.apply(any())).thenCallRealMethod();

        final NecessidadeResponse resultado = service.atualizarPedido(idNecessidade, request, userPrincipal);

        verify(repository).findByIdAndTipoPedido(any(), any());
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

        doThrow(ApiException.class).when(repository).findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE);

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

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(of(pedido));

        service.atualizarPedido(idNecessidade, request, userPrincipal);

        verify(repository).findByIdAndTipoPedido(any(), any());
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

        when(repository.findByIdAndTipoPedido(idNecessidade, TipoPedido.NECESSIDADE)).thenReturn(ofNullable(pedido));

        service.atualizarPedido(idNecessidade, request, userPrincipal);

        verifyNoMoreInteractions(repository);
        verifyNoInteractions(necessidadeResponseMapper);
    }

}
