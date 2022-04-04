package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.domain.enumeration.Pedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findAllByPedido(final Pedido pedido, final Pageable pageable);
}
