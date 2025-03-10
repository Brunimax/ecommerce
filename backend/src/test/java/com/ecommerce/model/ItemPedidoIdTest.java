package com.ecommerce.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ItemPedidoIdTest {

    @Test
    void testEquals_WhenSameIds_ShouldReturnTrue() {
        ItemPedidoId id1 = new ItemPedidoId(1L, 2L);
        ItemPedidoId id2 = new ItemPedidoId(1L, 2L);
        
        assertThat(id1).isEqualTo(id2);
    }

    @Test
    void testEquals_WhenDifferentPedidoId_ShouldReturnFalse() {
        ItemPedidoId id1 = new ItemPedidoId(1L, 2L);
        ItemPedidoId id2 = new ItemPedidoId(999L, 2L);
        
        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    void testEquals_WhenDifferentProdutoId_ShouldReturnFalse() {
        ItemPedidoId id1 = new ItemPedidoId(1L, 2L);
        ItemPedidoId id2 = new ItemPedidoId(1L, 999L);
        
        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    void testHashCode_WhenSameIds_ShouldBeEqual() {
        ItemPedidoId id1 = new ItemPedidoId(1L, 2L);
        ItemPedidoId id2 = new ItemPedidoId(1L, 2L);
        
        assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
    }

    @Test
    void testHashCode_WhenDifferentIds_ShouldNotBeEqual() {
        ItemPedidoId id1 = new ItemPedidoId(1L, 2L);
        ItemPedidoId id2 = new ItemPedidoId(3L, 4L);
        
        assertThat(id1.hashCode()).isNotEqualTo(id2.hashCode());
    }

}
