package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import br.com.ages.adoteumamanha.repository.PedidoRepository;

import java.util.function.Function;

@Component
public class InformacaoUsuarioResponseMapper implements Function<Usuario, InformacaoUsuarioResponse> {

    public InformacaoUsuarioResponse apply(final Usuario usuario) {
        return InformacaoUsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withAtivo(usuario.getAtivo())
                .withDocumento(usuario.getDocumento())
                .withEmail(usuario.getEmail())
                .withTelefone(usuario.getTelefone())
                .withPerfil(usuario.getPerfil())
                .withEndereco(usuario.getEndereco())
                .build();
        //findPedidosByIdUsuarioETipoDoacao --> separar a lista em aprovadas e recusadas ignorar as pendentes
    }

}

