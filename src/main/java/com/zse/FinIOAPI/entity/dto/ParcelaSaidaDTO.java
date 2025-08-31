package com.zse.FinIOAPI.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParcelaSaidaDTO {

	private Long id;

    private Integer numeroParcela;

    private BigDecimal valor;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    private String observacao;
	
}
