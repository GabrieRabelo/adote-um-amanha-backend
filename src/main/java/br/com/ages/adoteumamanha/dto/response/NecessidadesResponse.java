package br.com.ages.adoteumamanha.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public final class NecessidadesResponse implements Serializable {

    private static final long serialVersionUID = -129850719962068936L;

    private final List<NecessidadeResponse> necessidades;

}

