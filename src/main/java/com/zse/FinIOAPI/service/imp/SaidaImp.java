package com.zse.FinIOAPI.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.Saida;
import com.zse.FinIOAPI.entity.dto.SaidaDTO;
import com.zse.FinIOAPI.repository.SaidaRepository;
import com.zse.FinIOAPI.service.SaidaService;
import com.zse.FinIOAPI.service.convert.SaidaConvert;

@Service
public class SaidaImp implements SaidaService {

 private final SaidaRepository repository;
 private final SaidaConvert convert; 
 
 public SaidaImp(SaidaRepository repository, SaidaConvert convert) {
	 this.repository  = repository;
	 this.convert = convert;
 }


    @Override
    public SaidaDTO salvar(SaidaDTO dto) {
        return convert.convertEntityParaDto(repository.save(convert.convertDtoParaEntity(dto)));
    }

    @Override
    public SaidaDTO atualizar(Long id, SaidaDTO dto) {
        Saida existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saída não encontrada"));

        existente.setIdEmpresa(dto.getIdEmpresa());
        existente.setDescricao(dto.getDescricao());
        existente.setValorTotal(dto.getValorTotal());
        existente.setValorParcela(dto.getValorParcela());
        existente.setQuantidadeParcelas(dto.getQuantidadeParcelas());
        existente.setDataCompra(dto.getDataCompra());
        existente.setDataCadastro(dto.getDataCadastro());
        existente.setDataPagamento(dto.getDataPagamento());
        existente.setIdClassificacao(dto.getIdClassificacao());
        existente.setObservacao(dto.getObservacao());

        return convert.convertEntityParaDto(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SaidaDTO buscarPorId(Long id) {
    	 Saida existente = repository.findById(id).orElseThrow();
    	
    	return convert.convertEntityParaDto(existente);
    }

    @Override
    public List<SaidaDTO> listarTodos() {
    	return repository.findAll().stream().map(convert::convertEntityParaDto).toList(); 
    }
}
