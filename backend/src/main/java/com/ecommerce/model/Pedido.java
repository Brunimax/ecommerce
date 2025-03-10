package com.ecommerce.model;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    public static final String STATUS_CRIADO = "CRIADO";
    public static final String STATUS_PROCESSANDO = "PROCESSANDO";
    public static final String STATUS_ENTREGUE = "ENTREGUE";
    public static final String STATUS_CANCELADO = "CANCELADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false, length = 20)
    private String status;
    
    @ManyToOne
    private Endereco endereco;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}

