package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.CustomPageable;
import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public final class NecessidadesResponse implements CustomPageable<NecessidadeResponse> {

    private static final long serialVersionUID = 8877642249248887304L;

    private final Boolean ultimaPagina;
    private final Integer totalDePaginas;
    private final Long totalDeElementos;
    private final Integer numeroDaPagina;
    private final Integer tamanhoDaPagina;
    private final Boolean primeiraPagina;
    private final Integer numeroDeElementos;
    private final Boolean paginaVazia;
    private final List<NecessidadeResponse> conteudo;

    @Data
    @Builder(setterPrefix = "with")
    public static class NecessidadeResponse implements Serializable {

        private static final long serialVersionUID = -2663125773461547813L;

        private final Long id;
        private final String assunto;
        private final String descricao;
        private final Categoria categoria;
        private final Subcategoria subcategoria;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private final LocalDateTime name;
        private final Status status;

        private final Long idCasa;
        private final String nomeCasa;

    }
}


