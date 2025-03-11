package com.ecommerce.repository;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoId> {
    @Query("SELECT ip FROM ItemPedido ip WHERE ip.pedido.id = :pedidoId")
    List<ItemPedido> findByPedidoId(@Param("pedidoId") Long pedidoId);
}
