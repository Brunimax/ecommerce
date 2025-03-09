package com.ecommerce.controller;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.service.ItemPedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemPedidoController.class)
public class ItemPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemPedidoService itemPedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarItemPedido() throws Exception {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setQuantidade(2);
        when(itemPedidoService.criarItemPedido(any(ItemPedido.class))).thenReturn(itemPedido);

        mockMvc.perform(post("/api/itens-pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemPedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantidade").value(2));

        verify(itemPedidoService).criarItemPedido(any(ItemPedido.class));
    }

}

