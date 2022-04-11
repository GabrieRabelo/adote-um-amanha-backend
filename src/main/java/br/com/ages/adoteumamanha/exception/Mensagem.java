package br.com.ages.adoteumamanha.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {

    ACESSO_NAO_PERMITIDO("Credenciais de acesso inválida."),
    ASSUNTO_INVALIDO("Assunto inválido."),
    CASA_NAO_ENCONTRADA("Casa não encontrada"),
    CATEGORIA_INVALIDA("Categoria inválida."),
    DESCRICAO_INVALIDA("Descricão inválida."),
    EMAIL_INVALIDO("E-mail inválido."),
    ERRO_GENERICO("Erro no servidor."),
    NECESSIDADE_NAO_ENCONTRADA("Necessidade não encontrada"),
    PEDIDO_NAO_PODE_SER_DELETADO("Apenas o usuário criador do pedido poderá deleta-lo."),
    REQUEST_INVALIDO("Requisição inválida."),
    SENHA_INVALIDA("Senha inválida."),
    STATUS_NAO_PENDENTE("É possivel alterar pedidos que estejam como status como pendente ainda."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida.");
    private final String descricao;

}