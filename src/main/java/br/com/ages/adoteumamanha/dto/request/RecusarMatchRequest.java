package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public final class RecusarMatchRequest implements Serializable {

    private static final long serialVersionUID = -5756111733363597084L;
    private final String motivoReprovacao;

}
