package com.ecommerce.service;

import com.ecommerce.dto.PedidoMensagemDTO;
import com.ecommerce.dto.PedidoProdutosDTO;
import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Endereco;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.producer.PedidoProducer;
import com.ecommerce.repository.EnderecoRepository;
import com.ecommerce.repository.ItemPedidoRepository;
import com.ecommerce.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;
    private final ItemPedidoRepository itemPedidoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PedidoProducer pedidoProducer, ItemPedidoRepository itemPedidoRepository, EnderecoRepository enderecoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProducer = pedidoProducer;
        this.itemPedidoRepository = itemPedidoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public PedidoProdutosDTO buscarPedidoProdutosPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
        List<ItemPedido> itemPedidos = itemPedidoRepository.findByPedidoId(id);
        PedidoProdutosDTO pedidoProdutosDTO = new PedidoProdutosDTO();
        pedidoProdutosDTO.setPedido(pedido);
        List<ProdutoDTO> produtoDTOs = new ArrayList<>();
        for (ItemPedido itemPedido : itemPedidos) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setProduto(itemPedido.getProduto());
            produtoDTO.setQuantidade(itemPedido.getQuantidade());
            produtoDTOs.add(produtoDTO);
        }
        pedidoProdutosDTO.setProdutos(produtoDTOs);
        return pedidoProdutosDTO;
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        pedidoProducer.enviarMensagemCriacao(pedidoSalvo);

        return pedidoSalvo;
    }

    @Transactional
    public Pedido criarPedidoProdutos(PedidoProdutosDTO pedidoProdutosDTO) {
        Endereco endereco = enderecoRepository.save(pedidoProdutosDTO.getPedido().getEndereco());
        Pedido pedido = pedidoProdutosDTO.getPedido();
        pedido.setEndereco(endereco);
        pedido = pedidoRepository.save(pedido);

        for (ProdutoDTO produto : pedidoProdutosDTO.getProdutos()) {
            ItemPedido itemPedido = new ItemPedido();
            ItemPedidoId itemId = new ItemPedidoId();
            itemPedido.setId(itemId);
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto.getProduto());
            itemPedido.setQuantidade(produto.getQuantidade());
            itemPedido.setPrecoUnitario(calcularPrecoUnitario(produto.getProduto().getPreco(), produto.getQuantidade()));

            itemPedidoRepository.save(itemPedido);
        }

        return pedido;
    }

    private BigDecimal calcularPrecoUnitario(BigDecimal precoProduto, Integer quantidade) {
        if (precoProduto == null || quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Preço do produto e quantidade devem ser válidos");
        }
        return precoProduto.multiply(BigDecimal.valueOf(quantidade));
    }

    @Transactional
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    String statusOriginal = pedido.getStatus();

                    // Atualiza os dados do pedido
                    pedido.setNomeCliente(pedidoAtualizado.getNomeCliente());
                    pedido.setStatus(pedidoAtualizado.getStatus());
                    pedido.setEndereco(pedidoAtualizado.getEndereco());

                    Pedido pedidoSalvo = pedidoRepository.save(pedido);

                    // Publica mensagem de atualização no RabbitMQ somente se o status foi alterado
                    if (pedidoAtualizado.getStatus() != null && !pedidoAtualizado.getStatus().equals(statusOriginal)) {
                        pedidoProducer.enviarMensagemAtualizacao(pedidoAtualizado);
                    }

                    return pedidoSalvo;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    @Transactional
    public Pedido atualizarStatus(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));

        pedidoRepository.deleteById(id);

        pedidoProducer.enviarMensagemExclusao(pedido);
    }
}
