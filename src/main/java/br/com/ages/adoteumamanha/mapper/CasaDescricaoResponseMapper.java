package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CasaDescricaoResponseMapper implements Function<Usuario, CasaDescricaoResponse> {

    public CasaDescricaoResponse apply(final Usuario usuario) {
        return CasaDescricaoResponse.builder()
                .withSite(usuario.getSite())
                .withEndereco(usuario.getEndereco())
                .withEmail(usuario.getEmail())
                .withNome(usuario.getNome())
                .withTelefone(usuario.getTelefone())
                .build();
    }
}
