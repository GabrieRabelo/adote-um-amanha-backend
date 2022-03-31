package br.com.ages.adoteumamanha.converter;

import br.com.ages.adoteumamanha.domain.entity.UsuarioEntity;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CasaConverter {

    public static CasaDescricaoResponse paraResponse(UsuarioEntity usuarioEntity) {
        return CasaDescricaoResponse.builder()
                .withSite(usuarioEntity.getSite())
                .withEndereco(usuarioEntity.getEndereco())
                .withEmail(usuarioEntity.getEmail())
                .withNome(usuarioEntity.getNome())
                .withTelefone(usuarioEntity.getTelefone())
                .build();
    }
}
