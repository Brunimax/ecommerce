package com.ecommerce.controller;

import com.ecommerce.model.Endereco;
import com.ecommerce.service.EnderecoService;
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

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCriarEndereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua Teste");
        endereco.setCidade("Cidade Teste");
        when(enderecoService.criarEndereco(any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(post("/api/enderecos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rua").value("Rua Teste"))
                .andExpect(jsonPath("$.cidade").value("Cidade Teste"));

        verify(enderecoService).criarEndereco(any(Endereco.class));
    }

}
