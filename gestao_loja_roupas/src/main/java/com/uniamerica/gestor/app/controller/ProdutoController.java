package com.uniamerica.gestor.app.controller;

import java.math.BigDecimal;
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

import com.uniamerica.gestor.app.entity.Produto;
import com.uniamerica.gestor.app.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
		Produto novoProduto = produtoService.salvarProduto(produto);
		return ResponseEntity.ok(novoProduto);
	}

	@GetMapping
	public ResponseEntity<List<Produto>> listarTodos() {
		List<Produto> produtos = produtoService.listarTodos();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		Optional<Produto> produto = produtoService.buscarPorId(id);
		if (produto.isPresent()) {
			return ResponseEntity.ok(produto.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produto) {
		try {
			Produto updatedProduto = produtoService.update(id, produto);
			return ResponseEntity.ok(updatedProduto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/filter/nome")
	public List<Produto> findByNome(@RequestParam String nome) {
		return produtoService.findByNome(nome);
	}

	@GetMapping("/filter/preco-min")
	public List<Produto> findByPrecoGreaterThanEqual(@RequestParam BigDecimal preco) {
		return produtoService.findByPrecoGreaterThanEqual(preco);
	}

	@GetMapping("/filter/preco-range")
	public List<Produto> findByPrecoBetween(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
		return produtoService.findByPrecoBetween(minPrice, maxPrice);
	}
}