package br.com.ages.adoteumamanha.domain.enumeration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Perfil {

    ADMIN("ROLE_ADMIN"),
    CASA("ROLE_CASA"),
    DOADOR("ROLE_DOADOR");

    private final String descricao;

}

