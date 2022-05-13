package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsuarioResponseMapper implements Function<Usuario, UsuarioResponse> {

    public UsuarioResponse apply(final Usuario usuario) {
        return UsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withPerfil(usuario.getPerfil())
                .build();
    }

}
