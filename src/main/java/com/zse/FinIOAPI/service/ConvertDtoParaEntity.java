package com.zse.FinIOAPI.service;

public interface ConvertDtoParaEntity<T,A>{

	 A convertDtoParaEntity(T t);
	
	 T convertEntityParaDto(A a);
	
}
