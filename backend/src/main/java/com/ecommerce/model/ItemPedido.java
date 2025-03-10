package com.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    @EmbeddedId
    private ItemPedidoId id; // Chave composta (pedido_id + produto_id)

    @ManyToOne
    @MapsId("pedidoId") // Vincula ao pedidoId na chave composta
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false)
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId") // Vincula ao produtoId na chave composta
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)
    private Produto produto;

    private Integer quantidade;
    private BigDecimal precoUnitario;

    // Construtor padrão
    public ItemPedido() {}

    // Construtor com parâmetros
    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    // Getters e Setters
    public ItemPedidoId getId() {
        return id;
    }

    public void setId(ItemPedidoId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

}
