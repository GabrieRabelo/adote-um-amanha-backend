package br.com.ages.adoteumamanha.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {

    ERRO_GENERICO("Erro no servidor."),
    ACESSO_NAO_PERMITIDO("Credenciais de acesso inválida."),
    REQUEST_INVALIDO("Requisição inválida."),
    NECESSIDADE_NAO_ENCONTRADA("Necessidade não encontrada"),
    PEDIDO_NAO_PODE_SER_DELETADO("Apenas o usuário criador do pedido poderá deleta-lo."),
    STATUS_NAO_PENDENTE("É possivel apenas deletar pedidos que estejam como status como pendente ainda."),
    SENHA_INVALIDA("Senha inválida."),
    ASSUNTO_INVALIDO("Assunto inválido."),
    DESCRICAO_INVALIDA("Descricão inválida."),
    CATEGORIA_INVALIDA("Categoria inválida."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida."),
    EMAIL_INVALIDO("E-mail inválido.");

    private final String descricao;

}