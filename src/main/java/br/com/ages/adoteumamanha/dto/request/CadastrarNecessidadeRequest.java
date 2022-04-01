package br.com.ages.adoteumamanha.dto.request;

import br.com.ages.adoteumamanha.domain.enumeration.CategoriaEnum;
import br.com.ages.adoteumamanha.domain.enumeration.DonationEnum;
import br.com.ages.adoteumamanha.domain.enumeration.SubCategoriaEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public final class CadastrarNecessidadeRequest implements Serializable {

    private static final long serialVersionUID = -2655512771481957746L;

    private String assunto;

    private String descricao;

    private DonationEnum tipoDoacao;

    private CategoriaEnum categoria;

    private SubCategoriaEnum subCategoria;
}
