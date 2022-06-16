package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuarioResponse;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class ResumoUsuarioResponseMapper implements Function<Usuario, ResumoUsuarioResponse> {

    private final PedidoRepository pedidoRepository;

    public ResumoUsuarioResponseMapper(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public ResumoUsuarioResponse apply(final Usuario usuario) {
        return ofNullable(usuario)
                .map(u -> ResumoUsuarioResponse.builder()
                        .withId(u.getId())
                        .withNome(u.getNome())
                        .withDoacoesAprovadas(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(u.getId(), Status.FINALIZADA))
                        .withDoacoesRecusadas(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(u.getId(), Status.RECUSADO))
                        .build())
                .orElse(null);
    }
}


