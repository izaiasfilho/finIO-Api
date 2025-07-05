package com.zse.FinIOAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zse.FinIOAPI.entity.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
	
}
