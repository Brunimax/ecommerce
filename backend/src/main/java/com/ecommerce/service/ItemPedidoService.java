package com.ecommerce.service;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional
    public ItemPedido criarItemPedido(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public List<ItemPedido> listarTodosItensPedido() {
        return itemPedidoRepository.findAll();
    }

    public Optional<ItemPedido> buscarItemPedidoPorId(Long id) {
        return itemPedidoRepository.findById(id);
    }

    @Transactional
    public ItemPedido atualizarItemPedido(Long id, ItemPedido itemPedidoAtualizado) {
        return itemPedidoRepository.findById(id)
            .map(itemPedido -> {
                itemPedido.setProduto(itemPedidoAtualizado.getProduto());
                itemPedido.setQuantidade(itemPedidoAtualizado.getQuantidade());
                itemPedido.setPrecoUnitario(itemPedidoAtualizado.getPrecoUnitario());
                return itemPedidoRepository.save(itemPedido);
            })
            .orElseThrow(() -> new RuntimeException("Item de pedido não encontrado"));
    }

    @Transactional
    public void deletarItemPedido(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    public List<ItemPedido> buscarItensPorPedidoId(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }
}
