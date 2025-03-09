package com.ecommerce.service;

import com.ecommerce.model.Produto;
import com.ecommerce.repository.ProdutoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void testAtualizarProduto_Sucesso() {
        Produto produtoExistente = new Produto();
        produtoExistente.setId(1L);
        produtoExistente.setNome("Produto Antigo");
        produtoExistente.setPreco(BigDecimal.valueOf(100.0));

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Produto Novo");
        produtoAtualizado.setPreco(BigDecimal.valueOf(150.0));

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        Produto resultado = produtoService.atualizarProduto(1L, produtoAtualizado);
        
        assertEquals("Produto Novo", resultado.getNome());
        assertEquals(BigDecimal.valueOf(150.0), resultado.getPreco());
    }
}
