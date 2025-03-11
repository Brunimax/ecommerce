package com.ecommerce.dto;

import com.ecommerce.model.Produto;

public class ProdutoDTO {
    
    private Produto produto;
    private Integer quantidade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
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
}
