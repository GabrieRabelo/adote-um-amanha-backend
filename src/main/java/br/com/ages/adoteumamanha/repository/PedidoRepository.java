package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "SELECT p FROM Pedido p " +
            " WHERE ((coalesce(:categorias) is null  or (p.categoria in (:categorias)))" +
            " and (coalesce(:subcategorias) is null  or (p.subcategoria in (:subcategorias)))" +
            " and (coalesce(:status) is null or (p.status in (:status)))" +
            " and (coalesce(:data) is null or (p.dataHora >= (:data)))" +
            " and (coalesce(:idUsuarioLogado) is null or (p.usuario.id = (:idUsuarioLogado)))" +
            " and (p.tipoPedido = :tipoPedido)" +
            " and (coalesce(:texto) is null or (((lower(p.assunto) like(concat('%', lower(:texto), '%')))" +
            " or (lower(p.descricao) like(concat('%', lower(:texto), '%')))" +
            " or (lower(p.usuario.nome) like(concat('%', lower(:texto), '%')))))))")
    Page<Pedido> findAllPedidosPorFiltros(@Param("categorias") final List<Categoria> categorias,
                                          @Param("subcategorias") final List<Subcategoria> subcategorias,
                                          @Param("status") final List<Status> status,
                                          @Param("data") final LocalDateTime data,
                                          @Param("texto") final String textoBusca,
                                          @Param("tipoPedido") final TipoPedido tipoPedido,
                                          @Param("idUsuarioLogado") final Long idUsuarioLogado,
                                          final Pageable pageable);

    @Query(value = "SELECT p FROM Pedido p " +
            " WHERE ((coalesce(:tipos) is null  or (p.tipoPedido in (:tipos)))" +
            "and (coalesce(:idUsuario) is null  or (p.usuario.id = :idUsuario))" +
            "and p.id = :idPedido)")
    Optional<Pedido> findByIdPedidoAndTipoPedidoAndIdUsuario(@Param("idPedido") final Long idPedido,
                                                             @Param("tipos") final List<TipoPedido> tipoPedidos,
                                                             @Param("idUsuario") final Long idUsuario);

    @Query(value = "SELECT COUNT(p) FROM Pedido p " +
            " WHERE 1 = 1" +
            "and (coalesce(:idUsuario) is null  or (p.usuario.id = :idUsuario))" +
            "and p.status = :statusPedido")
    int findNumberByIdUsuarioAndTipoPedidoAndStatus(@Param("idUsuario") final Long idUsuario,
                                                    @Param("statusPedido") final Status statusPedido);

}
