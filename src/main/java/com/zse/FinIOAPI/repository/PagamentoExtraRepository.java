package com.zse.FinIOAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zse.FinIOAPI.entity.PagamentoExtra;

@Repository
public interface PagamentoExtraRepository extends JpaRepository<PagamentoExtra, Long> {
}
