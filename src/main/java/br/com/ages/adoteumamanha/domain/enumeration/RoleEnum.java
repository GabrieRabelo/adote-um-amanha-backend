package br.com.ages.adoteumamanha.domain.enumeration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {

    ADMIN("ROLE_ADMIN"),
    CASA("ROLE_CASA"),
    DOADOR("ROLE_DOADOR");

    private final String role;

}

