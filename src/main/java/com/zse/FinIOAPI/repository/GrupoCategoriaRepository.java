package com.zse.FinIOAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zse.FinIOAPI.entity.GrupoCategoria;

@Repository
public interface GrupoCategoriaRepository extends JpaRepository<GrupoCategoria, Long> {
}
