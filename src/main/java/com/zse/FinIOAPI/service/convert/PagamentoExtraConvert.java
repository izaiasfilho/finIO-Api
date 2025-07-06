package com.zse.FinIOAPI.service.convert;

import org.springframework.stereotype.Component;

import com.zse.FinIOAPI.entity.PagamentoExtra;
import com.zse.FinIOAPI.entity.dto.PagamentoExtraDTO;
import com.zse.FinIOAPI.service.ConvertDtoParaEntity;

@Component
public class PagamentoExtraConvert implements ConvertDtoParaEntity<PagamentoExtraDTO, PagamentoExtra>{

	@Override
	public PagamentoExtra convertDtoParaEntity(PagamentoExtraDTO t) {
	 if(t != null ) {
		 return PagamentoExtra.builder()
				 .id(t.getId())
				 .idEmpresa(t.getIdEmpresa())
				 .descricao(t.getDescricao())
				 .valor(t.getValor())
				 .dataPagamento(t.getDataPagamento())
				 .idClassificacao(t.getIdClassificacao())
				 .observacao(t.getObservacao())
				 .build();		 				  		 	
	 }
		return null;
	}
	@Override
	public PagamentoExtraDTO convertEntityParaDto(PagamentoExtra a) {	
		 if(a != null ) {
			 return PagamentoExtraDTO.builder()
					 .id(a.getId())
					 .idEmpresa(a.getIdEmpresa())
					 .descricao(a.getDescricao())
					 .valor(a.getValor())
					 .dataPagamento(a.getDataPagamento())
					 .idClassificacao(a.getIdClassificacao())
					 .observacao(a.getObservacao())
					 .build();
		 }
		return null;
	}
	
}
