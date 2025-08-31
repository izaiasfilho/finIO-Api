package com.zse.FinIOAPI.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.GrupoCategoria;
import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;
import com.zse.FinIOAPI.repository.GrupoCategoriaRepository;
import com.zse.FinIOAPI.service.GrupoCategoriaService;
import com.zse.FinIOAPI.service.convert.GrupoCategoriaConvert;

@Service
public class GrupoCategoriaIml implements GrupoCategoriaService {

    private  final GrupoCategoriaRepository  repository;
    private  final GrupoCategoriaConvert  convert;
      
    public GrupoCategoriaIml( GrupoCategoriaRepository repository, GrupoCategoriaConvert convert ) {   	
    	this.repository = repository;
    	this.convert = convert;
    }
    
    @Override
    public GrupoCategoriaDTO salvar(GrupoCategoriaDTO dto) {
          return convert.convertEntityParaDto(repository.save(convert.convertDtoParaEntity(dto)));
    }

    @Override
    public GrupoCategoriaDTO atualizar(Long id, GrupoCategoriaDTO dto) {       
    	GrupoCategoria grupoCategoria = repository.findById(id).orElseThrow(
    			() -> new RuntimeException("Grupo Categoria não encontrado")); 
    	
    	grupoCategoria.setDescricao(dto.getDescricao());
    	grupoCategoria.setIdTipoGrupo(dto.getIdTipoGrupo());
    	grupoCategoria.setAtivo(dto.getAtivo());
    	
    	return convert.convertEntityParaDto(repository.save(grupoCategoria));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GrupoCategoriaDTO buscarPorId(Long id) {  	
    	GrupoCategoria grupoCategoria = repository.findById(id).orElseThrow();   	
        return convert.convertEntityParaDto(grupoCategoria);
    }

    @Override
    public List<GrupoCategoriaDTO> listarTodos() {
    	return repository.findAll().stream().map(convert::convertEntityParaDto).toList(); 
    }
    
}
