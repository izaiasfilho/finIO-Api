package com.zse.FinIOAPI.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.Entrada;
import com.zse.FinIOAPI.entity.dto.EntradaDTO;
import com.zse.FinIOAPI.repository.EntradaRepository;
import com.zse.FinIOAPI.service.EntradaService;

@Service
public class EntradaImp implements EntradaService {

    @Autowired
    private EntradaRepository repository;

    private EntradaDTO toDTO(Entrada entrada) {
        return EntradaDTO.builder()
                .id(entrada.getId())
                .idEmpresa(entrada.getIdEmpresa())
                .descricao(entrada.getDescricao())
                .valor(entrada.getValor())
                .dataPrevista(entrada.getDataPrevista())
                .dataRecebimento(entrada.getDataRecebimento())
                .idFrequencia(entrada.getIdFrequencia())
                .observacao(entrada.getObservacao())
                .build();
    }

    private Entrada toEntity(EntradaDTO dto) {
        return Entrada.builder()
                .id(dto.getId())
                .idEmpresa(dto.getIdEmpresa())
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .dataPrevista(dto.getDataPrevista())
                .dataRecebimento(dto.getDataRecebimento())
                .idFrequencia(dto.getIdFrequencia())
                .observacao(dto.getObservacao())
                .build();
    }

    @Override
    public EntradaDTO salvar(EntradaDTO dto) {
        Entrada entrada = toEntity(dto);
        return toDTO(repository.save(entrada));
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

        return toDTO(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public EntradaDTO buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
    }

    @Override
    public List<EntradaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
