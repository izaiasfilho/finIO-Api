package com.zse.FinIOAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.dto.PagamentoExtraDTO;

@Service
public interface PagamentoExtraService {
    PagamentoExtraDTO salvar(PagamentoExtraDTO dto);
    PagamentoExtraDTO atualizar(Long id, PagamentoExtraDTO dto);
    void deletar(Long id);
    PagamentoExtraDTO buscarPorId(Long id);
    List<PagamentoExtraDTO> listarTodos();
}
