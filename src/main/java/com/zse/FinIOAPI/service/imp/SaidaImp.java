package com.zse.FinIOAPI.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.Saida;
import com.zse.FinIOAPI.entity.dto.SaidaDTO;
import com.zse.FinIOAPI.repository.SaidaRepository;
import com.zse.FinIOAPI.service.SaidaService;

@Service
public class SaidaImp implements SaidaService {

    @Autowired
    private SaidaRepository repository;

    private SaidaDTO toDTO(Saida entity) {
        return SaidaDTO.builder()
                .id(entity.getId())
                .idEmpresa(entity.getIdEmpresa())
                .descricao(entity.getDescricao())
                .valorTotal(entity.getValorTotal())
                .valorParcela(entity.getValorParcela())
                .quantidadeParcelas(entity.getQuantidadeParcelas())
                .dataCompra(entity.getDataCompra())
                .dataCadastro(entity.getDataCadastro())
                .dataPagamento(entity.getDataPagamento())
                .idClassificacao(entity.getIdClassificacao())
                .observacao(entity.getObservacao())
                .build();
    }

    private Saida toEntity(SaidaDTO dto) {
        return Saida.builder()
                .id(dto.getId())
                .idEmpresa(dto.getIdEmpresa())
                .descricao(dto.getDescricao())
                .valorTotal(dto.getValorTotal())
                .valorParcela(dto.getValorParcela())
                .quantidadeParcelas(dto.getQuantidadeParcelas())
                .dataCompra(dto.getDataCompra())
                .dataCadastro(dto.getDataCadastro())
                .dataPagamento(dto.getDataPagamento())
                .idClassificacao(dto.getIdClassificacao())
                .observacao(dto.getObservacao())
                .build();
    }

    @Override
    public SaidaDTO salvar(SaidaDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
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

        return toDTO(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SaidaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Saída não encontrada"));
    }

    @Override
    public List<SaidaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
