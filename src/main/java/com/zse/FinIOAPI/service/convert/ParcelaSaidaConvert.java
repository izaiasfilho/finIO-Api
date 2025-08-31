package com.zse.FinIOAPI.service.convert;

import org.springframework.stereotype.Component;

import com.zse.FinIOAPI.entity.ParcelaSaida;
import com.zse.FinIOAPI.entity.dto.ParcelaSaidaDTO;
import com.zse.FinIOAPI.service.ConvertDtoParaEntity;

@Component
public class ParcelaSaidaConvert implements ConvertDtoParaEntity<ParcelaSaidaDTO, ParcelaSaida> {
	
	@Override
	public ParcelaSaida convertDtoParaEntity(ParcelaSaidaDTO t) {
	 if(t != null ) {
		 return ParcelaSaida.builder()
				 .id(t.getId())
				 .numeroParcela(t.getNumeroParcela())
				 .valor(t.getValor())
				 .dataVencimento(t.getDataVencimento())
				 .dataPagamento(t.getDataPagamento())
				 .observacao(t.getObservacao())
				 .build();		 				  		 	
	 }
		return null;
	}
	@Override
	public ParcelaSaidaDTO convertEntityParaDto(ParcelaSaida a) {	
		 if(a != null ) {
			 return ParcelaSaidaDTO.builder()
					 .id(a.getId())
					 .numeroParcela(a.getNumeroParcela())
					 .valor(a.getValor())
					 .dataVencimento(a.getDataVencimento())
					 .dataPagamento(a.getDataPagamento())
					 .observacao(a.getObservacao())
					 .build();	
		 }
		return null;
	}

}
