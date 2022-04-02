package br.com.ages.adoteumamanha.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {

    ERRO_GENERICO("Erro no servidor."),
    REQUEST_INVALIDO("Requisição inválida."),
    SENHA_INVALIDA("Senha inválida."),
    ASSUNTO_INVALIDO("Assunto inválido."),
    DESCRICAO_INVALIDA("Descricão inválida."),
    CATEGORIA_INVALIDA("Categoria inválida."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida."),
    EMAIL_INVALIDO("E-mail inválido.");

    private final String descricao;

}