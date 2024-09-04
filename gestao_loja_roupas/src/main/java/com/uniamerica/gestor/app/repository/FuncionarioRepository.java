package com.uniamerica.gestor.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uniamerica.gestor.app.entity.Funcionario;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	 // Método automático baseado no nome
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);

    // Método automático baseado na idade
    List<Funcionario> findByIdadeLessThan(int idade);

    // Método JPQL para encontrar funcionários por parte da matrícula
    @Query("SELECT f FROM Funcionario f WHERE f.matricula LIKE %:matricula%")
    List<Funcionario> findByMatriculaLike(String matricula);
}

