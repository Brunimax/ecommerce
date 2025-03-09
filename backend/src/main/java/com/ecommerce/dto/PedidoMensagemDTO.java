package com.ecommerce.dto;

import com.ecommerce.model.Pedido;

public class PedidoMensagemDTO {

    private String acao;
    private Pedido pedido;

    public PedidoMensagemDTO(String acao, Pedido pedido) {
        this.acao = acao;
        this.pedido = pedido;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
