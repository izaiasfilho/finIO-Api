package com.zse.FinIOAPI.entity.enun;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum EnumFrequencia {
	UNICA(1L, "unica"),
	QUINZENAL(2L,"quinzenal"),
	MENSAL(3L,"mensal");
	
	private Long id;
    private String descricao;

    EnumFrequencia(long id, String descricao) {
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

    public static EnumFrequencia getById(long id) {
        for (EnumFrequencia tipo : values()) {
            if (tipo.id == id) {
                return tipo;
            }
        }
        return null; 
    }

    public static EnumFrequencia getByName(String name) {
        for (EnumFrequencia tipo : values()) {
            if (tipo.name().equalsIgnoreCase(name)) {
                return tipo;
            }
        }
        return null; 
    }
    
    @Converter(autoApply = true)
    public static class TipoUsuarioonverter implements AttributeConverter<EnumFrequencia, Long> {

        @Override
        public Long convertToDatabaseColumn(EnumFrequencia attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getId();
        }

        @Override
        public EnumFrequencia convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            return EnumFrequencia.getById(dbData);
        }
    }
}
