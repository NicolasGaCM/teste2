package com.uniamerica.gestor.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.uniamerica.gestor.app.dto.ClienteConsumoDTO;
import com.uniamerica.gestor.app.entity.Cliente;
import com.uniamerica.gestor.app.entity.Funcionario;
import com.uniamerica.gestor.app.entity.Venda;
import com.uniamerica.gestor.app.exceptions.ErrorResponse;
import com.uniamerica.gestor.app.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	@PostMapping
	public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
		Venda novaVenda = vendaService.salvarVenda(venda);
		return ResponseEntity.ok(novaVenda);
	}

	@GetMapping
	public ResponseEntity<List<Venda>> listarTodas() {
		List<Venda> vendas = vendaService.listarTodas();
		return ResponseEntity.ok(vendas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Optional<Venda> venda = vendaService.buscarPorId(id);
		if (venda.isPresent()) {
			return ResponseEntity.ok(venda.get());
		} else { 
			ErrorResponse errorResponse = new ErrorResponse("Venda não encontrada",
					"A venda com o ID " + id + " não foi encontrada no sistema.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/cliente-mais-consumiu")
	public List<ClienteConsumoDTO> getClienteQueMaisConsumiu() {
		return vendaService.getClienteQueMaisConsumiu();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
		vendaService.deletarVenda(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Venda> update(@PathVariable Long id, @RequestBody Venda venda) {
		try {
			Venda updatedVenda = vendaService.update(id, venda);
			return ResponseEntity.ok(updatedVenda);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/filter/cliente")
	public List<Venda> findByCliente(@RequestParam Cliente cliente) {
		return vendaService.findByCliente(cliente);
	}

	@GetMapping("/filter/funcionario")
	public List<Venda> findByFuncionario(@RequestParam Funcionario funcionario) {
		return vendaService.findByFuncionario(funcionario);
	}

	@GetMapping("/filter/valor-total")
	public List<Venda> findByValorTotalGreaterThan(@RequestParam BigDecimal valorTotal) {
		return vendaService.findByValorTotalGreaterThan(valorTotal);
	}
}