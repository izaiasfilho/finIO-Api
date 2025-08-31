package com.zse.FinIOAPI.entity;

import com.zse.FinIOAPI.util.ConvertUtil;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class UsuarioCredenciais {
	
	private String username;
	private String password;
	
	  @PrePersist
	    @PreUpdate
	    public void normalizeFields() {
	        this.username = ConvertUtil.normalize(this.username);

	    }
}
