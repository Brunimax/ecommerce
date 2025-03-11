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
    public ResponseEntity<List<ItemPedido>> listarTodosItens() {
        List<ItemPedido> itens = itemPedidoService.listarTodosItensPedido();
        return ResponseEntity.ok(itens);
    }

    
}
