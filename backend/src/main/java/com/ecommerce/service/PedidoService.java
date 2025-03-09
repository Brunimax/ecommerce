package com.ecommerce.service;

import com.ecommerce.model.Pedido;
import com.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
            .map(pedido -> {
                pedido.setNomeCliente(pedidoAtualizado.getNomeCliente());
                pedido.setStatus(pedidoAtualizado.getStatus());
                pedido.setEndereco(pedidoAtualizado.getEndereco());
                return pedidoRepository.save(pedido);
            })
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
