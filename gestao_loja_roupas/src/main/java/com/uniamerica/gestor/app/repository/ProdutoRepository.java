package com.uniamerica.gestor.app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uniamerica.gestor.app.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Método automático baseado no nome
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Método automático baseado no preço
    List<Produto> findByPrecoGreaterThanEqual(BigDecimal preco);

    // Método JPQL para encontrar produtos por um intervalo de preço
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :minPrice AND :maxPrice")
    List<Produto> findByPrecoBetween(BigDecimal minPrice, BigDecimal maxPrice);
}