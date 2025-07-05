package com.zse.FinIOAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grupo_categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(name = "id_enum_tipo_grupo")
    private Long idTipoGrupo;

    @Column(nullable = false)
    private Boolean ativo = true;
}
