package br.com.ages.adoteumamanha.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {

    ACESSO_DOACAO_NAO_PERMITIDA("Doação não pertence ao usuário conectado"),
    ACESSO_NAO_PERMITIDO("Credenciais de acesso inválida."),
    ASSUNTO_INVALIDO("Assunto inválido."),
    CASA_ACESSO_DOACAO("Perfil de casa não pode acessar doação"),
    CASA_NAO_ENCONTRADA("Casa não encontrada"),
    CATEGORIA_INVALIDA("Categoria inválida."),
    DESCRICAO_INVALIDA("Descricão inválida."),
    DOACAO_NAO_ENCONTRADA("Doação não encontrada"),
    EMAIL_INVALIDO("E-mail inválido."),
    ERRO_GENERICO("Erro no servidor."),
    ITEM_NAO_ENCONTRADO("Item não encontrado"),
    NECESSIDADE_NAO_ENCONTRADA("Necessidade não encontrada"),
    OPERACAO_NAO_PERMITIDA("Operação não permitida."),
    PEDIDO_NAO_PODE_SER_DELETADO("Apenas o usuário criador do pedido poderá deletá-lo."),
    REQUEST_INVALIDO("Requisição inválida."),
    SENHA_INVALIDA("Senha inválida."),
    STATUS_NAO_PENDENTE("Só é possível alterar pedidos que estejam com status pendente."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida."),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado");

    private final String descricao;
}