package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.model.Pedido;

public class PedidoProdutosDTO {
    private Pedido pedido;
    private List<ProdutoDTO> produtos;

    public PedidoProdutosDTO() {
    }

    public PedidoProdutosDTO(Pedido pedido, List<ProdutoDTO> produtos) {
        this.pedido = pedido;
        this.produtos = produtos;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }
}
