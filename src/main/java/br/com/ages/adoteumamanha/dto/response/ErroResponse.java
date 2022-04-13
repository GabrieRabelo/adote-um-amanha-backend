package br.com.ages.adoteumamanha.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public final class ErroResponse<T> implements Serializable {

    private static final long serialVersionUID = 6267754919995285475L;

    private Integer codigoStatus;
    private T informacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataHora;

    public ErroResponse() {
        this.dataHora = LocalDateTime.now();
    }
}
