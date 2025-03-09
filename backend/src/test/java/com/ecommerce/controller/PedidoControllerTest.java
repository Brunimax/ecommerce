package com.ecommerce.controller;

import com.ecommerce.model.Pedido;
import com.ecommerce.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.criarPedido(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(pedidoService).criarPedido(any(Pedido.class));
    }

    @Test
    public void testListarTodosPedidos() throws Exception {
        when(pedidoService.listarTodosPedidos()).thenReturn(Arrays.asList(new Pedido(), new Pedido()));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(pedidoService).listarTodosPedidos();
    }

    @Test
    public void testBuscarPedidoPorId() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.buscarPedidoPorId(1L)).thenReturn(pedido);

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(pedidoService).buscarPedidoPorId(1L);
    }

    @Test
    public void testAtualizarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.atualizarPedido(eq(1L), any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(pedidoService).atualizarPedido(eq(1L), any(Pedido.class));
    }

    @Test
    public void testDeletarPedido() throws Exception {
        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService).deletarPedido(1L);
    }
}
