package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dto.PedidoProdutosDTO;
import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.*;
import com.ecommerce.producer.PedidoProducer;
import com.ecommerce.repository.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoProducer pedidoProducer;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoProdutosDTO pedidoProdutosDTO;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNomeCliente("Cliente Teste");
        pedido.setStatus("CRIADO");
        pedido.setEndereco(endereco);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook");
        produto.setPreco(BigDecimal.valueOf(5000));

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setProduto(produto);
        produtoDTO.setQuantidade(2);

        pedidoProdutosDTO = new PedidoProdutosDTO();
        pedidoProdutosDTO.setPedido(pedido);
        pedidoProdutosDTO.setProdutos(Collections.singletonList(produtoDTO));
    }

    @Test
    void criarPedidoProdutos_DeveSalvarPedidoEItens() {
        // Arrange
        when(enderecoRepository.save(any())).thenReturn(pedido.getEndereco());
        when(pedidoRepository.save(any())).thenReturn(pedido);
        when(itemPedidoRepository.save(any())).thenReturn(new ItemPedido());

        // Act
        Pedido resultado = pedidoService.criarPedidoProdutos(pedidoProdutosDTO);

        // Assert
        assertNotNull(resultado);
        verify(enderecoRepository).save(any());
        verify(pedidoRepository).save(any());
        verify(itemPedidoRepository, atLeastOnce()).save(any());
    }

    @Test
    void atualizarPedido_QuandoExistir_DeveAtualizarDados() {
        // Arrange
        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setNomeCliente("Novo Nome");
        pedidoAtualizado.setStatus("PROCESSANDO");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any())).thenReturn(pedido);

        // Act
        Pedido resultado = pedidoService.atualizarPedido(1L, pedidoAtualizado);

        // Assert
        assertEquals("Novo Nome", resultado.getNomeCliente());
        verify(pedidoProducer).enviarMensagemAtualizacao(any());
    }

    @Test
    void atualizarStatus_QuandoPedidoNaoExiste_DeveLancarExcecao() {
        // Arrange
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.atualizarStatus(99L, "PROCESSANDO");
        });
    }

    @Test
    void deletarPedido_QuandoExistir_DeveChamarDelete() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).deleteById(1L);

        // Act
        pedidoService.deletarPedido(1L);

        // Assert
        verify(pedidoRepository).deleteById(1L);
        verify(pedidoProducer).enviarMensagemExclusao(any());
    }
}
