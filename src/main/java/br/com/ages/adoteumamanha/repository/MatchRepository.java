package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query(value = "SELECT m FROM Match m " +
            " WHERE ((coalesce(:categorias) is null or (m.necessidade.categoria in (:categorias)))" +
            " and (coalesce(:subcategorias) is null or (m.necessidade.subcategoria in (:subcategorias)))" +
            " and (coalesce(:data) is null or (m.data >= (:data)))" +
            " and (coalesce(:status) is null or (m.status in (:status)))" +
            " and (coalesce(:texto) is null or (((lower(m.necessidade.assunto) like(concat('%', lower(:texto), '%')))" +
            " or (lower(m.descricao) like(concat('%', lower(:texto), '%')))" +
            " or (lower(m.necessidade.usuario.nome) like(concat('%', lower(:texto), '%')))))))")
    Page<Match> findAllMatchesPorFiltro(@Param("categorias") final List<Categoria> categorias,
                                        @Param("subcategorias") final List<Subcategoria> subcategorias,
                                        @Param("data") final LocalDateTime data,
                                        @Param("texto") final String textoBusca,
                                        @Param("status") final List<Status> status,
                                        final Pageable pageable);
}