package com.zse.FinIOAPI.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PagamentoExtraDTO {

	private Long id;

    private Long idEmpresa;

    private String descricao;

    private BigDecimal valor;

    private LocalDate dataPagamento;

    private Long idClassificacao;

    private String observacao;
	
}
