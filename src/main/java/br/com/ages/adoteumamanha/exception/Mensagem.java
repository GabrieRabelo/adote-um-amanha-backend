package br.com.ages.adoteumamanha.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mensagem {

    ACESSO_DOACAO_NAO_PERMITIDA("Doação não pertence ao usuário conectado."),
    ACESSO_NAO_PERMITIDO("Credenciais de acesso inválida."),
    ASSUNTO_INVALIDO("Assunto inválido."),
    BAIRRO_INVALIDO("Bairro inválido."),
    CASA_ACESSO_DOACAO("Perfil de casa não pode acessar doação."),
    CASA_NAO_ENCONTRADA("Casa não encontrada."),
    CATEGORIA_INVALIDA("Categoria inválida."),
    CATEGORIA_MATCH_DIFERENTE("Categoria do match devem ser iguais."),
    CEP_INVALIDO("CEP inválido."),
    CIDADE_INVALIDA("Cidade inválida."),
    CPF_CNPJ_INVALIDO("CPF/CNPJ inválido."),
    DESCRICAO_INVALIDA("Descricão inválida."),
    DOACAO_MATCH_INVALIDO("Doação do match inválido."),
    DOACAO_NAO_ENCONTRADA("Doação não encontrada."),
    DOCUMENTO_INVALIDO("CPF/CNPJ inválido."),
    EMAIL_INVALIDO("E-mail inválido."),
    EMAIL_NAO_CADASTRADO("E-mail não cadastrado."),
    ERRO_GENERICO("Erro no servidor."),
    ESTADO_INVALIDO("Estado inválido."),
    ITEM_NAO_ENCONTRADO("Item não encontrado."),
    MATCH_INVALIDO("Match inválido."),
    MATCH_INEXISTENTE("Match não encontrado."),
    MATCH_FINALIZADA_OU_RECUSADA("Match já foi finalizado ou recusado no sistema."),
    NECESSIDADE_MATCH_INVALIDO("Necessidade do match inválida."),
    NECESSIDADE_NAO_ENCONTRADA("Necessidade não encontrada."),
    NOME_INVALIDO("Nome inválido."),
    NUMERO_CASA_INVALIDO("Número inválido."),
    OPERACAO_NAO_PERMITIDA("Operação não permitida."),
    REQUEST_INVALIDO("Requisição inválida."),
    RUA_INVALIDA("Rua inválida."),
    SENHA_INVALIDA("Senha inválida."),
    STATUS_NAO_PENDENTE("Só é possível alterar pedidos que estejam com status pendente."),
    SUBCATEGORIA_INVALIDA("Subcategoria inválida."),
    SUBCATGEORIA_MATCH_DIFERENTE("Subcategoria do match devem ser iguais."),
    TELEFONE_INVALIDO("Telefone inválido."),
    TOKEN_INVALIDO("Token inválido."),
    TOKEN_EXPIRADO("Token expirado."),
    TOKEN_USADO("Token já foi utilizado."),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado."),
    USUARIO_NAO_PODE_DELETAR_PEDIDO("Apenas o usuário criador do pedido poderá deletá-lo."),
    VINCULACAO_DE_DOACAO_COM_DOACAO_MATCH("Não pode vincular uma doação com outra doação."),
    MATCH_NAO_ENCONTRADO("Match não encontrado."),
    EMAIL_OU_CPF_JA_CADASTRADO("E-mail ou CPF já cadastrado.");

    private final String descricao;
}