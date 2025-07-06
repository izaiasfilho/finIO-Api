package com.zse.FinIOAPI.service.convert;

import org.springframework.stereotype.Component;

import com.zse.FinIOAPI.entity.Entrada;
import com.zse.FinIOAPI.entity.dto.EntradaDTO;
import com.zse.FinIOAPI.service.ConvertDtoParaEntity;

@Component
public class EntradaConvert implements ConvertDtoParaEntity<EntradaDTO , Entrada >{

	@Override
	public Entrada convertDtoParaEntity(EntradaDTO t) {
	 if(t != null ) {
		 return Entrada.builder()
				 .id(t.getId())
				 .idEmpresa(t.getIdEmpresa())
				 .descricao(t.getDescricao())
				 .valor(t.getValor())
				 .dataPrevista(t.getDataPrevista())
				 .dataRecebimento(t.getDataRecebimento())
				 .idFrequencia(t.getIdFrequencia())
				 .observacao(t.getObservacao())
				 .build();	 		 	
	 }
		return null;
	}
	@Override
	public EntradaDTO convertEntityParaDto(Entrada a) {
		if(a != null ) {
			 return EntradaDTO.builder()
					 .id(a.getId())
					 .idEmpresa(a.getIdEmpresa())
					 .descricao(a.getDescricao())
					 .valor(a.getValor())
					 .dataPrevista(a.getDataPrevista())
					 .dataRecebimento(a.getDataRecebimento())
					 .idFrequencia(a.getIdFrequencia())
					 .observacao(a.getObservacao())
					 .build();	 		 	
		 }
		return null;
	}

}
