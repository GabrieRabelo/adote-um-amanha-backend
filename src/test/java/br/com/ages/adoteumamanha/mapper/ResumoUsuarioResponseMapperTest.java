package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuarioResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResumoUsuarioResponseMapperTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private ResumoUsuarioResponseMapper mapper;

    @Test
    void ok() {

        final Usuario usuario = Fixture.make(Usuario.builder()).build();


        when(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(usuario.getId(), Status.FINALIZADA)).thenReturn(1);
        when(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(usuario.getId(), Status.RECUSADO)).thenReturn(2);

        final ResumoUsuarioResponse response = mapper.apply(usuario);

        assertEquals(response.getId(), usuario.getId());
        assertEquals(response.getNome(), usuario.getNome());
        assertEquals(1, response.getDoacoesAprovadas());
        assertEquals(2, response.getDoacoesRecusadas());

    }
}