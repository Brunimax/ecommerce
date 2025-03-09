package com.ecommerce.service;

import com.ecommerce.model.Endereco;
import com.ecommerce.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Endereco criarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarTodosEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    @Transactional
    public Endereco atualizarEndereco(Long id, Endereco enderecoAtualizado) {
        return enderecoRepository.findById(id)
            .map(endereco -> {
                endereco.setRua(enderecoAtualizado.getRua());
                endereco.setBairro(enderecoAtualizado.getBairro());
                endereco.setCidade(enderecoAtualizado.getCidade());
                endereco.setUf(enderecoAtualizado.getUf());
                endereco.setCep(enderecoAtualizado.getCep());
                return enderecoRepository.save(endereco);
            })
            .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    @Transactional
    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}
