package com.ecommerce.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ecommerce.dto.PedidoProdutosDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Pedido;
import com.ecommerce.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    private Pedido pedido;
    private PedidoProdutosDTO pedidoProdutosDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
        
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNomeCliente("Cliente Teste");
        
        pedidoProdutosDTO = new PedidoProdutosDTO();
        pedidoProdutosDTO.setPedido(pedido);
    }

    @Test
    void criarPedido_DeveRetornar201() throws Exception {
        // Arrange
        when(pedidoService.criarPedidoProdutos(any())).thenReturn(pedido);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoProdutosDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarPedidoProdutosPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        // Arrange
        when(pedidoService.buscarPedidoProdutosPorId(99L))
            .thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pedidos/pedidosProdutos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizarStatus_DeveRetornar200() throws Exception {
        // Arrange
        when(pedidoService.atualizarStatus(anyLong(), anyString())).thenReturn(pedido);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/pedidos/1/status")
                .param("status", "PROCESSANDO"))
                .andExpect(status().isOk());
    }

    @Test
    void deletarPedido_DeveRetornar204() throws Exception {
        // Arrange
        doNothing().when(pedidoService).deletarPedido(1L);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());
    }
}
