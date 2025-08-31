package com.zse.FinIOAPI.service.convert;

import org.springframework.stereotype.Component;

import com.zse.FinIOAPI.entity.Saida;
import com.zse.FinIOAPI.entity.dto.SaidaDTO;
import com.zse.FinIOAPI.service.ConvertDtoParaEntity;

@Component
public class SaidaConvert implements ConvertDtoParaEntity<SaidaDTO, Saida> {

	@Override
	public Saida convertDtoParaEntity(SaidaDTO t) {
	 if(t != null ) {
		 return Saida.builder()
				 .id(t.getId())
				 .idEmpresa(t.getIdEmpresa())
				 .descricao(t.getDescricao())
				 .valorTotal(t.getValorTotal())
				 .valorParcela(t.getValorParcela())
				 .quantidadeParcelas(t.getQuantidadeParcelas())
				 .dataCompra(t.getDataCompra())
				 .dataCadastro(t.getDataCadastro())
				 .dataPagamento(t.getDataPagamento())
				 .idClassificacao(t.getIdClassificacao())
				 .observacao(t.getObservacao())
				 .build();		 				  		 	
	 }
		return null;
	}
	@Override
	public SaidaDTO convertEntityParaDto(Saida a) {	
		 if(a != null ) {
			 return SaidaDTO.builder()
					 .id(a.getId())
					 .idEmpresa(a.getIdEmpresa())
					 .descricao(a.getDescricao())
					 .valorTotal(a.getValorTotal())
					 .valorParcela(a.getValorParcela())
					 .quantidadeParcelas(a.getQuantidadeParcelas())
					 .dataCompra(a.getDataCompra())
					 .dataCadastro(a.getDataCadastro())
					 .dataPagamento(a.getDataPagamento())
					 .idClassificacao(a.getIdClassificacao())
					 .observacao(a.getObservacao())
					 .build();			 				  		 	
		 }
		return null;
	}
	
}
