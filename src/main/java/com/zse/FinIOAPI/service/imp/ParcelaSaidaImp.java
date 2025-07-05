package com.zse.FinIOAPI.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.Saida;
import com.zse.FinIOAPI.entity.dto.ParcelaSaidaDTO;
import com.zse.FinIOAPI.repository.ParcelaSaidaRepository;
import com.zse.FinIOAPI.service.ParcelaSaidaService;

@Service
public class ParcelaSaidaImp implements ParcelaSaidaService {

    @Autowired
    private ParcelaSaidaRepository repository;

    private ParcelaSaidaDTO toDTO(Saida entity) {
        return ParcelaSaidaDTO.builder()
                .id(entity.getId())
                .valor(entity.getValorParcela())
                .dataPagamento(entity.getDataPagamento())
                .observacao(entity.getObservacao())
                .build();
    }

    private Saida toEntity(ParcelaSaidaDTO dto) {
        return Saida.builder()
                .id(dto.getId())
                .valorParcela(dto.getValor())
                .dataPagamento(dto.getDataPagamento())
                .observacao(dto.getObservacao())
                .build();
    }

    @Override
    public ParcelaSaidaDTO salvar(ParcelaSaidaDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public ParcelaSaidaDTO atualizar(Long id, ParcelaSaidaDTO dto) {
        Saida existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Saída não encontrada"));

        existente.setValorParcela(dto.getValor());
        existente.setDataPagamento(dto.getDataPagamento());
        existente.setObservacao(dto.getObservacao());

        return toDTO(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ParcelaSaidaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Saída não encontrada"));
    }

    @Override
    public List<ParcelaSaidaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
