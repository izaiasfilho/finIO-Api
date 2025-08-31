package com.zse.FinIOAPI.entity.enun;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum EnumClassificacao {
    NECESSARIA(1L,"necessaria"),
    OBRIGATORIA(2L,"obrigatoria"),
    IMPULSO(3L,"impulso"),
    SUPERFLUA(4L,"superflua");
	
	private Long id;
    private String descricao;

    EnumClassificacao(long id, String descricao) {
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

    public static EnumClassificacao getById(long id) {
        for (EnumClassificacao tipo : values()) {
            if (tipo.id == id) {
                return tipo;
            }
        }
        return null; 
    }

    public static EnumClassificacao getByName(String name) {
        for (EnumClassificacao tipo : values()) {
            if (tipo.name().equalsIgnoreCase(name)) {
                return tipo;
            }
        }
        return null; 
    }
    
    @Converter(autoApply = true)
    public static class TipoUsuarioonverter implements AttributeConverter<EnumClassificacao, Long> {

        @Override
        public Long convertToDatabaseColumn(EnumClassificacao attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getId();
        }

        @Override
        public EnumClassificacao convertToEntityAttribute(Long dbData) {
            if (dbData == null) {
                return null;
            }
            return EnumClassificacao.getById(dbData);
        }
    }
}


