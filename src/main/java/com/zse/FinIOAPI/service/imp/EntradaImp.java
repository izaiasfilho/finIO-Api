package com.zse.FinIOAPI.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.Entrada;
import com.zse.FinIOAPI.entity.dto.EntradaDTO;
import com.zse.FinIOAPI.repository.EntradaRepository;
import com.zse.FinIOAPI.service.EntradaService;
import com.zse.FinIOAPI.service.convert.EntradaConvert;

@Service
public class EntradaImp implements EntradaService {

    @Autowired
    private EntradaRepository repository;

    @Autowired
    private EntradaConvert convert;
    
    @Override
    public EntradaDTO salvar(EntradaDTO dto) {
    	return convert.convertEntityParaDto(repository.save(convert.convertDtoParaEntity(dto)));
    }

    @Override
    public EntradaDTO atualizar(Long id, EntradaDTO dto) {
        Entrada existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));

        existente.setDescricao(dto.getDescricao());
        existente.setValor(dto.getValor());
        existente.setDataPrevista(dto.getDataPrevista());
        existente.setDataRecebimento(dto.getDataRecebimento());
        existente.setIdFrequencia(dto.getIdFrequencia());
        existente.setObservacao(dto.getObservacao());

        return convert.convertEntityParaDto(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public EntradaDTO buscarPorId(Long id) {
    	Entrada entrada = repository.findById(id).orElseThrow();   	
      return convert.convertEntityParaDto(entrada);
    }

    @Override
    public List<EntradaDTO> listarTodos() {
    	  	
        return repository.findAll().stream().map(convert::convertEntityParaDto).toList();        
    }
}
