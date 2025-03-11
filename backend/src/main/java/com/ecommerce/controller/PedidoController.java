package com.ecommerce.controller;

import com.ecommerce.dto.PedidoProdutosDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Pedido;
import com.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@Valid @RequestBody PedidoProdutosDTO pedidoProdutosDTO) {
        try {
            Pedido pedidoCriado = pedidoService.criarPedidoProdutos(pedidoProdutosDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
        } catch (Exception e) {
            e.printStackTrace(); // Log no console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o pedido: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/pedidosProdutos/{id}")
    public ResponseEntity<PedidoProdutosDTO> buscarPedidoProdutosPorId(@PathVariable Long id) {
        try {
            PedidoProdutosDTO pedidoProdutosDTO = pedidoService.buscarPedidoProdutosPorId(id);
            return ResponseEntity.ok(pedidoProdutosDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
