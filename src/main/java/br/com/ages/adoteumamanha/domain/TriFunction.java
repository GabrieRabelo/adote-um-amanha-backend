package br.com.ages.adoteumamanha.domain;

import java.io.Serializable;

public interface TriFunction<U, T, S, R> extends Serializable {

    R apply(U u, T t, S sa);

}
