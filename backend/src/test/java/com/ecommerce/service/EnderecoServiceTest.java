package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.EnderecoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    @DisplayName("Deve deletar endereço com sucesso")
    void testDeletarEndereco_Sucesso() {
        when(enderecoRepository.existsById(1L)).thenReturn(true);
        
        enderecoService.deletarEndereco(1L);
        
        verify(enderecoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar endereço inexistente")
    void testDeletarEndereco_NaoEncontrado() {
        when(enderecoRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.deletarEndereco(1L);
        });
    }
}

