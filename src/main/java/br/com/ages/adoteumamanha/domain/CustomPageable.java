package br.com.ages.adoteumamanha.domain;

import java.io.Serializable;
import java.util.List;

public interface CustomPageable<T> extends Serializable {
    Boolean getUltimaPagina();

    Integer getTotalDePaginas();

    Long getTotalDeElementos();

    Integer getNumeroDaPagina();

    Integer getTamanhoDaPagina();

    Boolean getPrimeiraPagina();

    Integer getNumeroDeElementos();

    Boolean getPaginaVazia();

    List<T> getConteudo();

}
