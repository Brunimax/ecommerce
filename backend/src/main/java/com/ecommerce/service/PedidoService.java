package com.ecommerce.service;

import com.ecommerce.dto.PedidoMensagemDTO;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Pedido;
import com.ecommerce.producer.PedidoProducer;
import com.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoProducer pedidoProducer;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PedidoProducer pedidoProducer) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProducer = pedidoProducer;
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

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        pedidoProducer.enviarMensagemCriacao(pedidoSalvo);

        return pedidoSalvo;
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
    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));

        pedidoRepository.deleteById(id);

        pedidoProducer.enviarMensagemExclusao(pedido);
    }
}
