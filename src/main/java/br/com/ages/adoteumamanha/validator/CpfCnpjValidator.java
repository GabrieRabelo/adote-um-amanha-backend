package br.com.ages.adoteumamanha.validator;


import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class CpfCnpjValidator {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public void validar(String cpfCnpj) {
        if (isFalse(isValidCPF(cpfCnpj) || isValidCNPJ(cpfCnpj))) {
            throw new ApiException(Mensagem.CPF_CNPJ_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
    }

    private Integer calcularDigito(final String str, final int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private String padLeft(final String text, final Character character) {
        return String.format("%11s", text).replace(' ', character);
    }

    private Boolean isValidCPF(String cpf) {
        cpf = cpf.trim().replaceAll("\\D", "");
        if ((cpf == null) || (cpf.length() != 11)) return false;

        for (int j = 0; j < 10; j++)
            if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(cpf))
                return false;

        final Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        final Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private Boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.trim().replaceAll("\\D", "");
        if ((cnpj == null) || (cnpj.length() != 14)) return false;

        final Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        final Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }
}