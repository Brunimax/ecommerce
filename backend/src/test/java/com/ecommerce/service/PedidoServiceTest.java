package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Pedido;
import com.ecommerce.repository.PedidoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve buscar pedido por ID com sucesso")
    void testBuscarPedidoPorId_Sucesso() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.buscarPedidoPorId(1L);
        
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não encontrado")
    void testBuscarPedidoPorId_NaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.buscarPedidoPorId(1L);
        });
    }

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void testCriarPedido_Sucesso() {
        Pedido novoPedido = new Pedido();
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(novoPedido);

        Pedido resultado = pedidoService.criarPedido(novoPedido);
        
        assertNotNull(resultado);
        verify(pedidoRepository).save(novoPedido);
    }
}
