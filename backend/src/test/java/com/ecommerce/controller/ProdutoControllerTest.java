package com.ecommerce.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Produto;
import com.ecommerce.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setPreco(BigDecimal.valueOf(5000.0));
    }

    @Test
    void criarProduto_DeveRetornar201() throws Exception {
        // Arrange
        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void listarTodosProdutos_DeveRetornar200() throws Exception {
        // Arrange
        when(produtoService.listarTodosProdutos()).thenReturn(List.of(produto));

        // Act & Assert
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Notebook"));
    }

    @Test
    void buscarProdutoPorId_QuandoExistir_DeveRetornar200() throws Exception {
        // Arrange
        when(produtoService.buscarProdutoPorId(1L)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook"));
    }

    @Test
    void buscarProdutoPorId_QuandoNaoExistir_DeveRetornar404() throws Exception {
        // Arrange
        when(produtoService.buscarProdutoPorId(2L)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizarProduto_QuandoExistir_DeveRetornar200() throws Exception {
        // Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Notebook Atualizado");
        produtoAtualizado.setPreco(BigDecimal.valueOf(6000.0));

        when(produtoService.atualizarProduto(eq(1L), any(Produto.class))).thenReturn(produtoAtualizado);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook Atualizado"));
    }

    @Test
    void atualizarProduto_QuandoNaoExistir_DeveRetornar404() throws Exception {
        // Arrange
        when(produtoService.atualizarProduto(eq(2L), any(Produto.class)))
            .thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarProduto_QuandoNaoExistir_DeveRetornar404() throws Exception {
        // Arrange
        doThrow(ResourceNotFoundException.class).when(produtoService).deletarProduto(2L);

        // Act & Assert
        mockMvc.perform(delete("/api/produtos/2"))
                .andExpect(status().isNotFound());
    }
}
