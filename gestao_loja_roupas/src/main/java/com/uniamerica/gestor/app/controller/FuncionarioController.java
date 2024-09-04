package com.uniamerica.gestor.app.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uniamerica.gestor.app.entity.Funcionario;
import com.uniamerica.gestor.app.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@PostMapping
	public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario funcionario) {
		Funcionario novoFuncionario = funcionarioService.salvarFuncionario(funcionario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoFuncionario.getId())
				.toUri();
		return ResponseEntity.created(uri).body(novoFuncionario);
	}

	@GetMapping
	public ResponseEntity<List<Funcionario>> listarTodos() {
		List<Funcionario> funcionarios = funcionarioService.listarTodos();
		return ResponseEntity.ok(funcionarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
		if (funcionario.isPresent()) {
			return ResponseEntity.ok(funcionario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody Funcionario funcionario) {
		try {
			Funcionario updatedFuncionario = funcionarioService.update(id, funcionario);
			return ResponseEntity.ok(updatedFuncionario);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
		funcionarioService.deletarFuncionario(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/filter/nome")
	public List<Funcionario> findByNome(@RequestParam String nome) {
		return funcionarioService.findByNome(nome);
	}

	@GetMapping("/filter/idade")
	public List<Funcionario> findByIdadeLessThan(@RequestParam int idade) {
		return funcionarioService.findByIdadeLessThan(idade);
	}

	@GetMapping("/filter/matricula")
	public List<Funcionario> findByMatriculaLike(@RequestParam String matricula) {
		return funcionarioService.findByMatriculaLike(matricula);
	}

}