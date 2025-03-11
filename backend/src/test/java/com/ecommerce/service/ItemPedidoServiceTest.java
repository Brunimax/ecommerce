package com.ecommerce.service;

import com.ecommerce.model.ItemPedido;
import com.ecommerce.repository.ItemPedidoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;


}
