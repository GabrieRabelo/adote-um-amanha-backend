package br.com.ages.adoteumamanha.service.pedidos.filtros.impl;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.service.pedidos.filtros.BuscarPedidosComFiltrosService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.BEM;
import static br.com.ages.adoteumamanha.domain.enumeration.Direcao.ASC;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.SAUDE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class BuscarNecessidadesComoDoadorServiceTest {

    @InjectMocks
    private BuscarNecessidadesComoDoadorService service;

    @Mock
    private BuscarPedidosComFiltrosService buscarPedidosComFiltrosService;

    @Captor
    private ArgumentCaptor<List<Status>> statusArgumentCaptor;

    @Test
    public void ok() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";
        final List<Categoria> categorias = asList(BEM);
        final List<Subcategoria> subcategorias = asList(SAUDE);
        final List<Status> statuses = asList(PENDENTE);
        final Integer mesesCorte = 6;
        final String texto = "Texto";

        service.buscar(pagina, tamanho, ordenacao, direcao, categorias, subcategorias, statuses, mesesCorte, texto, null);

        verify(buscarPedidosComFiltrosService).buscar(eq(pagina), eq(tamanho), eq(ordenacao), eq(direcao), eq(categorias),
                eq(subcategorias), eq(statuses), eq(mesesCorte), eq(texto), eq(NECESSIDADE), eq(null));
    }

    @Test
    public void remove_status_finalizada_filtro() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Direcao direcao = ASC;
        final String ordenacao = "data";
        final List<Categoria> categorias = asList(BEM);
        final List<Subcategoria> subcategorias = asList(SAUDE);
        final List<Status> statuses = new LinkedList(Arrays.asList(FINALIZADA));
        final Integer mesesCorte = 6;
        final String texto = "Texto";

        service.buscar(pagina, tamanho, ordenacao, direcao, categorias, subcategorias, statuses, mesesCorte, texto, null);

        verify(buscarPedidosComFiltrosService).buscar(eq(pagina), eq(tamanho), eq(ordenacao), eq(direcao), eq(categorias),
                eq(subcategorias), statusArgumentCaptor.capture(), eq(mesesCorte), eq(texto), eq(NECESSIDADE), eq(null));

        assertFalse(statusArgumentCaptor.getValue().contains(FINALIZADA));
    }
}
