package com.zse.FinIOAPI.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamento_extra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idEmpresa;

    @Column(length = 150, nullable = false)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "id_enum_classificacao")
    private Long idClassificacao;

    @Column(columnDefinition = "TEXT")
    private String observacao;
}
