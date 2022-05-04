package br.com.ages.adoteumamanha.dto.response;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@ToString
@SuperBuilder(setterPrefix = "with")
public class NecessidadeResponse extends DoacaoResponse implements Serializable {

    private static final long serialVersionUID = 8877612349734887304L;

    private final Long idCasa;
    private final String nomeCasa;
    private final String urlVideo;
}