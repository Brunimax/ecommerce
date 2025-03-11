package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setPreco(BigDecimal.valueOf(5000.0));
    }

    @Test
    void listarTodosProdutos_DeveRetornarListaDeProdutos() {
        // Arrange
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));

        // Act
        List<Produto> produtos = produtoService.listarTodosProdutos();

        // Assert
        assertEquals(1, produtos.size());
        verify(produtoRepository).findAll();
    }

    @Test
    void buscarProdutoPorId_QuandoExistir_DeveRetornarProduto() {
        // Arrange
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Act
        Produto encontrado = produtoService.buscarProdutoPorId(1L);

        // Assert
        assertNotNull(encontrado);
        assertEquals("Notebook", encontrado.getNome());
        verify(produtoRepository).findById(1L);
    }

    @Test
    void buscarProdutoPorId_QuandoNaoExistir_DeveLancarExcecao() {
        // Arrange
        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.buscarProdutoPorId(2L);
        });
    }

    @Test
    void criarProduto_DeveSalvarERetornarProduto() {
        // Arrange
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // Act
        Produto salvo = produtoService.criarProduto(produto);

        // Assert
        assertNotNull(salvo);
        assertEquals(1L, salvo.getId());
        verify(produtoRepository).save(produto);
    }

    @Test
    void atualizarProduto_QuandoExistir_DeveAtualizarERetornarProduto() {
        // Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Notebook Atualizado");
        produtoAtualizado.setPreco(BigDecimal.valueOf(6000.0));

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // Act
        Produto resultado = produtoService.atualizarProduto(1L, produtoAtualizado);

        // Assert
        assertEquals("Notebook Atualizado", resultado.getNome());
        assertEquals(6000.0, resultado.getPreco());
        verify(produtoRepository).findById(1L);
        verify(produtoRepository).save(produto);
    }

    @Test
    void atualizarProduto_QuandoNaoExistir_DeveLancarExcecao() {
        // Arrange
        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.atualizarProduto(2L, new Produto());
        });
    }

    @Test
    void deletarProduto_QuandoExistir_DeveDeletar() {
        // Arrange
        when(produtoRepository.existsById(1L)).thenReturn(true);

        // Act
        produtoService.deletarProduto(1L);

        // Assert
        verify(produtoRepository).deleteById(1L);
    }

    @Test
    void deletarProduto_QuandoNaoExistir_DeveLancarExcecao() {
        // Arrange
        when(produtoRepository.existsById(2L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.deletarProduto(2L);
        });
    }
}
