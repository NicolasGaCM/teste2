package com.uniamerica.gestor.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.gestor.app.entity.Produto;
import com.uniamerica.gestor.app.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto salvarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> buscarPorId(Long id) {
		return produtoRepository.findById(id);
	}

	public Produto update(Long id, Produto produto) {
		if (produtoRepository.existsById(id)) {
			produto.setId(id); // Garantir que o ID está correto
			return produtoRepository.save(produto); // Salvar o produto atualizado
		} else {
			throw new IllegalArgumentException("Produto não encontrado.");
		}
	}

	public void deletarProduto(Long id) {
		produtoRepository.deleteById(id);
	}

	public List<Produto> findByNome(String nome) {
		return produtoRepository.findByNomeContainingIgnoreCase(nome);
	}

	public List<Produto> findByPrecoGreaterThanEqual(BigDecimal preco) {
		return produtoRepository.findByPrecoGreaterThanEqual(preco);
	}

	public List<Produto> findByPrecoBetween(BigDecimal minPrice, BigDecimal maxPrice) {
		return produtoRepository.findByPrecoBetween(minPrice, maxPrice);
	}
}