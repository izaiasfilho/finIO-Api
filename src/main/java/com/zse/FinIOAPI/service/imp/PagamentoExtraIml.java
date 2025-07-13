package com.zse.FinIOAPI.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.PagamentoExtra;
import com.zse.FinIOAPI.entity.dto.PagamentoExtraDTO;
import com.zse.FinIOAPI.repository.PagamentoExtraRepository;
import com.zse.FinIOAPI.service.PagamentoExtraService;
import com.zse.FinIOAPI.service.convert.PagamentoExtraConvert;

@Service
public class PagamentoExtraIml implements PagamentoExtraService {

	private final PagamentoExtraRepository repository;
	private final PagamentoExtraConvert convert;
	
	public PagamentoExtraIml(PagamentoExtraRepository repository, PagamentoExtraConvert convert ) {		
		this.repository = repository;
		this.convert = convert;
	}

    @Override
    public PagamentoExtraDTO salvar(PagamentoExtraDTO dto) {
        return convert.convertEntityParaDto(repository.save(convert.convertDtoParaEntity(dto)));
    }

    @Override
    public PagamentoExtraDTO atualizar(Long id, PagamentoExtraDTO dto) {
        PagamentoExtra existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento extra não encontrado"));

        existente.setDescricao(dto.getDescricao());
        existente.setValor(dto.getValor());
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
    public PagamentoExtraDTO buscarPorId(Long id) {
        
    	PagamentoExtra pagamentoExtra = repository.findById(id).orElseThrow(); 	
    	return convert.convertEntityParaDto(pagamentoExtra);
    }

    @Override
    public List<PagamentoExtraDTO> listarTodos() {
        return repository.findAll().stream().map(convert::convertEntityParaDto).toList(); 
    }
}
