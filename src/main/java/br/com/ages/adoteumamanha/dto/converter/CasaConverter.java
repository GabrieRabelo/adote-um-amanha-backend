package br.com.ages.adoteumamanha.dto.converter;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CasaConverter {

    public static CasaDescricaoResponse paraResponse(Usuario usuario) {
        return CasaDescricaoResponse.builder()
                .withSite(usuario.getSite())
                .withEndereco(usuario.getEndereco())
                .withEmail(usuario.getEmail())
                .withNome(usuario.getNome())
                .withTelefone(usuario.getTelefone())
                .build();
    }
}
