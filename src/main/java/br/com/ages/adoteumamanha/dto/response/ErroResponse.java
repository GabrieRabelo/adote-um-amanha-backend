package br.com.ages.adoteumamanha.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public final class ErroResponse<T> implements Serializable {

    private static final long serialVersionUID = 7880262768730611523L;

    private Integer codigoStatus;
    private T informacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dataHora;

    public ErroResponse() {
        this.dataHora = LocalDateTime.now();
    }

}
