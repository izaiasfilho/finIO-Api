package com.zse.FinIOAPI.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EntradaDTO {

    private Long id;

    private Long idEmpresa;

    private String descricao;

    private BigDecimal valor;

    private LocalDate dataPrevista;

    private LocalDate dataRecebimento;

    private Long idFrequencia;

    private String observacao;
}
