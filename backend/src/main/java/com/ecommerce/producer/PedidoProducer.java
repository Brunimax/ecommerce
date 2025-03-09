package com.ecommerce.producer;

import com.ecommerce.dto.PedidoMensagemDTO;
import com.ecommerce.model.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ecommerce.config.RabbitMQConfig.QUEUE_NAME;

@Component
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PedidoProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarMensagemCriacao(Pedido pedido) {
        enviarMensagem("CRIADO", pedido);
    }

    public void enviarMensagemAtualizacao(Pedido pedido) {
        enviarMensagem("ATUALIZADO", pedido);
    }

    public void enviarMensagemExclusao(Pedido pedido) {
        enviarMensagem("EXCLUÍDO", pedido);
    }

    private void enviarMensagem(String acao, Pedido pedido) {
        try {
            PedidoMensagemDTO mensagemDTO = new PedidoMensagemDTO(acao, pedido);

            String mensagem = objectMapper.writeValueAsString(mensagemDTO);

            rabbitTemplate.convertAndSend(QUEUE_NAME, mensagem);
            System.out.println("Mensagem enviada para a fila: " + mensagem);
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao serializar o pedido: " + e.getMessage());
        }
    }
}
