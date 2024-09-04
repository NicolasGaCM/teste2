package com.uniamerica.gestor.app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uniamerica.gestor.app.entity.Cliente;
import com.uniamerica.gestor.app.entity.Funcionario;
import com.uniamerica.gestor.app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	   @Query("SELECT v.cliente AS cliente, SUM(v.valorTotal) AS totalGasto " +
	           "FROM Venda v " +
	           "GROUP BY v.cliente " +
	           "ORDER BY totalGasto DESC")
	    List<Object[]> findClienteQueMaisConsumiu();  ///JDBC
	    
	    // Método automático baseado no cliente
	    List<Venda> findByCliente(Cliente cliente);

	    // Método automático baseado no funcionário
	    List<Venda> findByFuncionario(Funcionario funcionario);

	    // Método JPQL para encontrar vendas por valor total maior que um determinado valor
	    @Query("SELECT v FROM Venda v WHERE v.valorTotal > :valorTotal")
	    List<Venda> findByValorTotalGreaterThan(BigDecimal valorTotal);
}