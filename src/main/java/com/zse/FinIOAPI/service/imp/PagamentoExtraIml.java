package com.zse.FinIOAPI.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.PagamentoExtra;
import com.zse.FinIOAPI.entity.dto.PagamentoExtraDTO;
import com.zse.FinIOAPI.repository.PagamentoExtraRepository;
import com.zse.FinIOAPI.service.PagamentoExtraService;

@Service
public class PagamentoExtraIml implements PagamentoExtraService {

    @Autowired
    private PagamentoExtraRepository repository;

    private PagamentoExtraDTO toDTO(PagamentoExtra entity) {
        return PagamentoExtraDTO.builder()
                .id(entity.getId())
                .idEmpresa(entity.getIdEmpresa())
                .descricao(entity.getDescricao())
                .valor(entity.getValor())
                .dataPagamento(entity.getDataPagamento())
                .idClassificacao(entity.getIdClassificacao())
                .observacao(entity.getObservacao())
                .build();
    }

    private PagamentoExtra toEntity(PagamentoExtraDTO dto) {
        return PagamentoExtra.builder()
                .id(dto.getId())
                .idEmpresa(dto.getIdEmpresa())
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .dataPagamento(dto.getDataPagamento())
                .idClassificacao(dto.getIdClassificacao())
                .observacao(dto.getObservacao())
                .build();
    }

    @Override
    public PagamentoExtraDTO salvar(PagamentoExtraDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
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

        return toDTO(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PagamentoExtraDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Pagamento extra não encontrado"));
    }

    @Override
    public List<PagamentoExtraDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
