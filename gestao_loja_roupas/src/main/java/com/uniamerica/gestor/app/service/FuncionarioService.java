package com.uniamerica.gestor.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.gestor.app.entity.Funcionario;
import com.uniamerica.gestor.app.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public Funcionario salvarFuncionario(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	public List<Funcionario> listarTodos() {
		return funcionarioRepository.findAll();
	}

	public Optional<Funcionario> buscarPorId(Long id) {
		return funcionarioRepository.findById(id);
	}

	public Funcionario update(Long id, Funcionario funcionario) {
		if (funcionarioRepository.existsById(id)) {
			funcionario.setId(id); // Garantir que o ID está correto
			return funcionarioRepository.save(funcionario); // Salvar o funcionário atualizado
		} else {
			throw new IllegalArgumentException("Funcionário não encontrado.");
		}
	}

	public void deletarFuncionario(Long id) {
		funcionarioRepository.deleteById(id);
	}

	public List<Funcionario> findByNome(String nome) {
		return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
	}

	public List<Funcionario> findByIdadeLessThan(int idade) {
		return funcionarioRepository.findByIdadeLessThan(idade);
	}

	public List<Funcionario> findByMatriculaLike(String matricula) {
		return funcionarioRepository.findByMatriculaLike(matricula);
	}
}