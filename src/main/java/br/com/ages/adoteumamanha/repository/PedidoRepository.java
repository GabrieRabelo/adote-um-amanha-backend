package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findAllByTipoPedido(final TipoPedido tipoPedido, final Pageable pageable);

    Page<Pedido> findAllByTipoPedidoAndStatus(final TipoPedido necessidade, final Status status, final Pageable pageable);

    Optional<Pedido> findByIdAndTipoPedido(Long id, TipoPedido tipoPedido);

    Page<Pedido> findAllByUsuarioIdAndStatus(Long id, Status status, final Pageable pageable);
}
