package com.zse.FinIOAPI.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.ParcelaSaida;
import com.zse.FinIOAPI.entity.dto.ParcelaSaidaDTO;
import com.zse.FinIOAPI.repository.ParcelaSaidaRepository;
import com.zse.FinIOAPI.service.ParcelaSaidaService;
import com.zse.FinIOAPI.service.convert.ParcelaSaidaConvert;

@Service
public class ParcelaSaidaImp implements ParcelaSaidaService {

	private final ParcelaSaidaRepository repository;
	private final ParcelaSaidaConvert convert;
	
	public ParcelaSaidaImp(ParcelaSaidaRepository repository, ParcelaSaidaConvert convert) {
		this.repository = repository;
		this.convert = convert;
	}

    @Override
    public ParcelaSaidaDTO salvar(ParcelaSaidaDTO dto) {
        return convert.convertEntityParaDto(repository.save(convert.convertDtoParaEntity(dto)));
    }
      

    @Override
    public ParcelaSaidaDTO atualizar(Long id, ParcelaSaidaDTO dto) {
    	ParcelaSaida existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcela Saída não encontrada"));

        existente.setNumeroParcela(dto.getNumeroParcela());
        existente.setValor(dto.getValor());
        existente.setDataVencimento(dto.getDataVencimento());
        existente.setDataPagamento(dto.getDataPagamento());

        return convert.convertEntityParaDto(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ParcelaSaidaDTO buscarPorId(Long id) {
    	ParcelaSaida ParcelaSaida = repository.findById(id).orElseThrow();
    	
    	return convert.convertEntityParaDto(ParcelaSaida);
    }

    @Override
    public List<ParcelaSaidaDTO> listarTodos() {
    	return repository.findAll().stream().map(convert::convertEntityParaDto).toList(); 
}
}
