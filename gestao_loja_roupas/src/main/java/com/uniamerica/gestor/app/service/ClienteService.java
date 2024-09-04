package com.uniamerica.gestor.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.gestor.app.entity.Cliente;
import com.uniamerica.gestor.app.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente update(Long id, Cliente cliente) {
		if (clienteRepository.existsById(id)) {
			cliente.setId(id); // Garantir que o ID está correto
			return clienteRepository.save(cliente); // Salvar o cliente atualizado
		} else {
			throw new IllegalArgumentException("Cliente não encontrado.");
		}
	}

	public void deletarCliente(Long id) {
		clienteRepository.deleteById(id);
	}

	public List<Cliente> findByNome(String nome) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome);
	}

	public List<Cliente> findByIdadeGreaterThanEqual(int idade) {
		return clienteRepository.findByIdadeGreaterThanEqual(idade);
	}

	public List<Cliente> findByCpfLike(String cpf) {
		return clienteRepository.findByCpfLike(cpf);
	}
}