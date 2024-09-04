package com.uniamerica.gestor.app.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false) // Cliente sempre presente
	private Cliente cliente;

	@ManyToOne(optional = false) // Funcionário sempre presente
	private Funcionario funcionario;

	@ManyToMany(cascade = CascadeType.ALL) // Produtos persistidos em cascata
	@NotEmpty(message = "A lista de produtos não pode estar vazia")
	@JoinTable(name = "venda_produtos")
	private List<Produto> produtos;

	@NotNull(message = "O valor total é obrigatório")
	private BigDecimal valorTotal;

	private String enderecoEntrega;
	
}
