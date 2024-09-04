package com.uniamerica.gestor.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uniamerica.gestor.app.entity.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
    // Método automático baseado no nome
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Método automático baseado na idade
    List<Cliente> findByIdadeGreaterThanEqual(int idade);

    // Método JPQL para encontrar clientes por parte do CPF
    @Query("SELECT c FROM Cliente c WHERE c.cpf LIKE %:cpf%")
    List<Cliente> findByCpfLike(String cpf);
}


