package com.zse.FinIOAPI.entity.enun;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum EnumTipoGrupo {
	ENTRADA(1L,""),
	SAIDA(2L,"");
	
	private Long id;
    private String descricao;

    EnumTipoGrupo(long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static EnumTipoGrupo getById(long id) {
        for (EnumTipoGrupo tipo : values()) {
            if (tipo.id == id) {
                return tipo;
            }
        }
        return null; 
    }

    public static EnumTipoGrupo getByName(String name) {
        for (EnumTipoGrupo tipo : values()) {
            if (tipo.name().equalsIgnoreCase(name)) {
                return tipo;
            }
        }
        return null; 
    }
    
    @Converter(autoApply = true)
    public static class TipoUsuarioonverter implements AttributeConverter<EnumTipoGrupo, Long> {

        @Override
        public Long convertToDatabaseColumn(EnumTipoGrupo attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getId();
        }

        @Override
        public EnumTipoGrupo convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            return EnumTipoGrupo.getById(dbData);
        }
    }
	
}
