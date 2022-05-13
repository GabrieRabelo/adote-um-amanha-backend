package br.com.ages.adoteumamanha.service.pedidos.filtros;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.mapper.PedidosResponseMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.BEM;
import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.ASC;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.SAUDE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@RunWith(MockitoJUnitRunner.class)
public class BuscarPedidosComFiltrosServiceTest {

    @InjectMocks
    private BuscarPedidosComFiltrosService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidosResponseMapper pedidosResponseMapper;

    @Captor
    private ArgumentCaptor<LocalDateTime> dataCorteCaptor;

    @Test
    public void ok() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";
        final Long idUsuarioLogado = 1L;

        final Pageable paging = PageRequest.of(pagina, tamanho, by(Direction.valueOf(direcao.name()), ordenacao));
        final List<Pedido> pedidos = asList(make(Pedido.builder()).build());

        final List<Categoria> categorias = asList(BEM);
        final List<Subcategoria> subcategorias = asList(SAUDE);
        final List<Status> statuses = asList(PENDENTE);
        final Integer mesesCorte = 6;
        final String texto = "Texto";
        final TipoPedido tipoPedido = NECESSIDADE;

        final Page<Pedido> pagedResponse = new PageImpl(pedidos);

        when(repository.findAllPedidosPorFiltros(
                any(List.class),
                any(List.class),
                any(List.class),
                any(LocalDateTime.class),
                any(String.class),
                any(TipoPedido.class),
                any(Long.class),
                any(Pageable.class)))
                .thenReturn(pagedResponse);

        service.buscar(pagina, tamanho, ordenacao, direcao, categorias, subcategorias, statuses, mesesCorte, texto, tipoPedido, idUsuarioLogado);

        verify(pedidosResponseMapper).apply(pagedResponse);
        verify(repository).findAllPedidosPorFiltros(eq(categorias), eq(subcategorias), eq(statuses),
                dataCorteCaptor.capture(), eq(texto), eq(NECESSIDADE), eq(idUsuarioLogado), eq(paging));
    }

}
