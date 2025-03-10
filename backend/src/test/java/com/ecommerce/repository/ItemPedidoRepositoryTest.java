package com.ecommerce.repository;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemPedidoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveItemPedido_WithCompositeKey_ShouldPersistCorrectly() {
        // Cria entidades relacionadas
        Pedido pedido = new Pedido();
        Produto produto = new Produto("Smartphone", BigDecimal.valueOf(2000));
        entityManager.persist(pedido);
        entityManager.persist(produto);

        // Cria ItemPedido com chave composta
        ItemPedido item = new ItemPedido();
        item.setId(new ItemPedidoId(pedido.getId(), produto.getId()));
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(2000));

        // Persiste e verifica
        ItemPedido savedItem = entityManager.persistAndFlush(item);
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getQuantidade()).isEqualTo(2);
    }
}
