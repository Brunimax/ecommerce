package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<ItemPedido> listarTodosItensPedido() {
        return itemPedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ItemPedido buscarItemPedidoPorId(Long id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado com id: " + id));
    }

    @Transactional
    public ItemPedido criarItemPedido(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    @Transactional
    public ItemPedido atualizarItemPedido(Long id, ItemPedido itemAtualizado) {
        return itemPedidoRepository.findById(id)
                .map(item -> {
                    item.setQuantidade(itemAtualizado.getQuantidade());
                    item.setProduto(itemAtualizado.getProduto());
                    item.setPrecoUnitario(itemAtualizado.getPrecoUnitario());
                    return itemPedidoRepository.save(item);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado com id: " + id));
    }

    @Transactional
    public void deletarItemPedido(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item do pedido não encontrado com id: " + id);
        }
        itemPedidoRepository.deleteById(id);
    }
}
