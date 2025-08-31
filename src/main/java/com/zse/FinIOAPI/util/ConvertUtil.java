package com.zse.FinIOAPI.util;

import java.text.Normalizer;

public class ConvertUtil {

	/**
     * Normaliza uma string removendo acentos, caracteres especiais, 
     * espaços extras e convertendo para minúsculas.
     * 
     * import static com.tec4z.posmei.util.SecurityUtil.idEmpresa;
     *
     * @param input String a ser normalizada
     * @return String normalizada
     */
    public static String normalize(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^\\p{ASCII}]", ""); 

        return normalized.toLowerCase().trim();
    }
	
}
