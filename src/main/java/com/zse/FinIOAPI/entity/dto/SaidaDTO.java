package com.zse.FinIOAPI.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaidaDTO {

	  private Long id;

	    private Long idEmpresa;

	    private String descricao;

	    private BigDecimal valorTotal;

	    private BigDecimal valorParcela;

	    private Integer quantidadeParcelas;

	    private LocalDate dataCompra;

	    private LocalDate dataCadastro;

	    private LocalDate dataPagamento;

	    private Long idClassificacao;

	    private String observacao;
	
}
