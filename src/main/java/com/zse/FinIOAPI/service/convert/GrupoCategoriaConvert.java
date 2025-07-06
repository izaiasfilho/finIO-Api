package com.zse.FinIOAPI.service.convert;

import org.springframework.stereotype.Component;

import com.zse.FinIOAPI.entity.GrupoCategoria;
import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;
import com.zse.FinIOAPI.service.ConvertDtoParaEntity;

@Component
public class GrupoCategoriaConvert implements ConvertDtoParaEntity<GrupoCategoriaDTO, GrupoCategoria> {
	
	@Override
	public GrupoCategoria convertDtoParaEntity(GrupoCategoriaDTO t) {
	 if(t != null ) {
		 return GrupoCategoria.builder()
				 .id(t.getId())
				 .descricao(t.getDescricao())
				 .idTipoGrupo(t.getIdTipoGrupo())
				 .ativo(t.getAtivo())
				 .build();		 				  		 	
	 }
		return null;
	}
	@Override
	public GrupoCategoriaDTO convertEntityParaDto(GrupoCategoria a) {	
		 if(a != null ) {
			 return GrupoCategoriaDTO.builder()
					 .id(a.getId())
					 .descricao(a.getDescricao())
					 .idTipoGrupo(a.getIdTipoGrupo())
					 .ativo(a.getAtivo())
					 .build();		 				  		 	
		 }
		return null;
	}

}
