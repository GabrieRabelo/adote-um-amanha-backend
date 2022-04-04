package br.com.ages.adoteumamanha.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public final class ErroResponse<T> implements Serializable {

    private static final long serialVersionUID = 6267754919995285475L;

    private Integer statusCode;
    private T data;
    private Long timeStamp;

    public ErroResponse() {
        this.timeStamp = System.currentTimeMillis();
    }
}
