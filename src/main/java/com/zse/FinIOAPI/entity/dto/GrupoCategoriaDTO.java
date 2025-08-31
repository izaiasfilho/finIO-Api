package com.zse.FinIOAPI.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GrupoCategoriaDTO {

	private Long id;

    private String descricao;

    private Long idTipoGrupo;

    private Boolean ativo;
}
