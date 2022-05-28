package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.TriFunction;
import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.ADMIN;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.MATCH_PENDENTE;
import static java.util.Objects.isNull;

@Component
public class MatchMapper implements TriFunction<UserPrincipal, Pedido, Pedido, Match> {

    final String DESCRICAO_DOACAO_VINCULADA = "Match de doação vinculada";
    final String DESCRICAO_MATCH_ADMIN = "Match feito pelo administrador do sistema";

    @Override
    public Match apply(final UserPrincipal userPrincipal, final Pedido doacao, final Pedido necessidade) {

        if (isNull(doacao) || isNull(necessidade) || isNull(userPrincipal)) {
            return null;
        }

        return Match.builder()
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .withDataCriacao(LocalDateTime.now())
                .withDescricao(buildDescricao(userPrincipal))
                .withStatus(buildStatus(userPrincipal))
                .build();
    }

    private String buildDescricao(final UserPrincipal userPrincipal) {
        if (ADMIN.equals(userPrincipal.getPerfil())) {
            return DESCRICAO_MATCH_ADMIN;
        }
        return DESCRICAO_DOACAO_VINCULADA;
    }

    private Status buildStatus(final UserPrincipal userPrincipal) {
        if (ADMIN.equals(userPrincipal.getPerfil())) {
            return FINALIZADA;
        }
        return MATCH_PENDENTE;
    }
}
