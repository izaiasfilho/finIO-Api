package com.zse.FinIOAPI.entity.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GrupoCategoriaDTO {

	private Long id;

    private String descricao;

    private Long idTipoGrupo;

    private Boolean ativo = true;
}
