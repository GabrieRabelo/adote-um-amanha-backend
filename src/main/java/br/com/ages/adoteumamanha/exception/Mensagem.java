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
    PEDIDO_NAO_PODE_SER_DELETADO("Apenas o usuário criador do pedido poderá deletá-lo."),
    REQUEST_INVALIDO("Requisição inválida."),
    SENHA_INVALIDA("Senha inválida."),
    STATUS_NAO_PENDENTE("Só é possível alterar pedidos que estejam com status pendente."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida."),
    NOME_INVALIDO("Nome inválido"),
    DOCUMENTO_INVALIDO("CPF/CNPJ inválido"),
    CEP_INVALIDO("CEP inválido"),
    BAIRRO_INVALIDO("Bairro inválido"),
    RUA_INVALIDA("Rua inválida"),
    NUMERO_RUA_INVALIDO("Número inválido"),
    TELEFONE_INVALIDO("Telefone inválido");

    private final String descricao;
}