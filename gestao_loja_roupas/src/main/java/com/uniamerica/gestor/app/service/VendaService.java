package com.uniamerica.gestor.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniamerica.gestor.app.dto.ClienteConsumoDTO;
import com.uniamerica.gestor.app.entity.Cliente;
import com.uniamerica.gestor.app.entity.Funcionario;
import com.uniamerica.gestor.app.entity.Produto;
import com.uniamerica.gestor.app.entity.Venda;
import com.uniamerica.gestor.app.repository.ClienteRepository;
import com.uniamerica.gestor.app.repository.FuncionarioRepository;
import com.uniamerica.gestor.app.repository.ProdutoRepository;
import com.uniamerica.gestor.app.repository.VendaRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public List<Venda> listarTodas() {
		return vendaRepository.findAll();
	}

	public Optional<Venda> buscarPorId(Long id) {
		return vendaRepository.findById(id);
	}

	public Venda update(Long id, Venda venda) {

		if (!vendaRepository.existsById(id)) {
			throw new IllegalArgumentException("Venda não encontrada.");
		}
		List<Produto> produtos = chargeProducts(venda);

		Cliente cliente = chargeClient(venda);

		Funcionario funcionario = chargeEmployee(venda);

		// Atualiza os detalhes da venda
		venda.setId(id);
		venda.setProdutos(produtos);
		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);

		return vendaRepository.save(venda);
	}

	private Funcionario chargeEmployee(Venda venda) {
		// Carrega o funcionário do banco de dados
		Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(venda.getFuncionario().getId());
		if (!funcionarioOptional.isPresent()) {
			throw new IllegalArgumentException("Funcionário não encontrado.");
		}
		Funcionario funcionario = funcionarioOptional.get();
		return funcionario;
	}

	private Cliente chargeClient(Venda venda) {
		// Carrega o cliente do banco de dados
		Optional<Cliente> clienteOptional = clienteRepository.findById(venda.getCliente().getId());
		if (!clienteOptional.isPresent()) {
			throw new IllegalArgumentException("Cliente não encontrado.");
		}
		Cliente cliente = clienteOptional.get();
		return cliente;
	}

	private List<Produto> chargeProducts(Venda venda) {
		// Carrega os produtos do banco de dados com base nos IDs fornecidos
		// Cria uma nova lista para armazenar os produtos carregados
		List<Produto> produtos = new ArrayList<>();

		// Carrega os produtos do banco de dados com base nos IDs fornecidos
		for (Produto produto : venda.getProdutos()) {
			Optional<Produto> produtoOptional = produtoRepository.findById(produto.getId());

			// Verifica se o produto existe
			if (produtoOptional.isPresent()) {
				produtos.add(produtoOptional.get()); // Adiciona o produto completo à lista
			} else {
				throw new IllegalArgumentException("Produto com ID " + produto.getId() + " não encontrado.");
			}
		}
		return produtos;
	}

	public void deletarVenda(Long id) {
		vendaRepository.deleteById(id);
	}

	public Venda salvarVenda(Venda venda) {
		// Recupera e valida os produtos da venda
		List<Produto> produtos = recuperarEValidarProdutos(venda.getProdutos());

		// Calcula o valor total da venda
		BigDecimal valorTotal = calcularValorTotal(produtos);

		// Recupera e valida o cliente da venda
		Cliente clienteRecuperado = validarCliente(venda.getCliente().getId());

		// Verifica as restrições de negócio
		verificarRestricoesDeNegocio(clienteRecuperado, valorTotal);

		// Atualiza a venda com os produtos e valor total calculado
		venda.setProdutos(produtos);
		venda.setValorTotal(valorTotal);

		// Salva a venda no repositório
		return vendaRepository.save(venda);
	}

	private List<Produto> recuperarEValidarProdutos(List<Produto> produtos) {
		List<Produto> produtosValidados = new ArrayList<>();
		for (Produto produto : produtos) {
			Produto produtoExistente = produtoRepository.findById(produto.getId())
					.orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + produto.getId()));
			produtosValidados.add(produtoExistente);
		}
		return produtosValidados;
	}

	private BigDecimal calcularValorTotal(List<Produto> produtos) {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (Produto produto : produtos) {
			valorTotal = valorTotal.add(produto.getPreco());
		}
		return valorTotal;
	}

	private Cliente validarCliente(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado: ID " + clienteId));
	}

	private void verificarRestricoesDeNegocio(Cliente cliente, BigDecimal valorTotal) {
		if (cliente.getIdade() < 18 && valorTotal.compareTo(new BigDecimal("500")) > 0) {
			throw new RuntimeException("O valor total não pode exceder R$ 500 para clientes menores de 18 anos.");
		}
	}

	public List<ClienteConsumoDTO> getClienteQueMaisConsumiu() {
		List<Object[]> results = vendaRepository.findClienteQueMaisConsumiu();
		return results.stream()
				.map(result -> new ClienteConsumoDTO(((Cliente) result[0]).getNome(), (BigDecimal) result[1]))
				.collect(Collectors.toList());
	}

	public List<Venda> findByCliente(Cliente cliente) {
		return vendaRepository.findByCliente(cliente);
	}

	public List<Venda> findByFuncionario(Funcionario funcionario) {
		return vendaRepository.findByFuncionario(funcionario);
	}

	public List<Venda> findByValorTotalGreaterThan(BigDecimal valorTotal) {
		return vendaRepository.findByValorTotalGreaterThan(valorTotal);
	}

}