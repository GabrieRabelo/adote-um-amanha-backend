package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CasaDescricaoResponseMapper implements Function<Usuario, CasaResponse> {

    public CasaResponse apply(final Usuario usuario) {
        return CasaResponse.builder()
                .withSite(usuario.getSite())
                .withEndereco(usuario.getEndereco())
                .withEmail(usuario.getEmail())
                .withNome(usuario.getNome())
                .withTelefone(usuario.getTelefone())
                .build();
    }

}
