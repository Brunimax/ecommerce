package com.ecommerce.controller;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedido> criarItemPedido(@RequestBody ItemPedido itemPedido) {
        ItemPedido novoItemPedido = itemPedidoService.criarItemPedido(itemPedido);
        return new ResponseEntity<>(novoItemPedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemPedido>> listarTodosItensPedido() {
        List<ItemPedido> itensPedido = itemPedidoService.listarTodosItensPedido();
        return ResponseEntity.ok(itensPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> buscarItemPedidoPorId(@PathVariable Long id) {
        return itemPedidoService.buscarItemPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> atualizarItemPedido(@PathVariable Long id, @RequestBody ItemPedido itemPedido) {
        try {
            ItemPedido itemPedidoAtualizado = itemPedidoService.atualizarItemPedido(id, itemPedido);
            return ResponseEntity.ok(itemPedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItemPedido(@PathVariable Long id) {
        itemPedidoService.deletarItemPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedido>> buscarItensPorPedidoId(@PathVariable Long pedidoId) {
        List<ItemPedido> itens = itemPedidoService.buscarItensPorPedidoId(pedidoId);
        return ResponseEntity.ok(itens);
    }
}
