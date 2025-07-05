package com.zse.FinIOAPI.service;

import com.zse.FinIOAPI.entity.dto.PagamentoExtraDTO;
import java.util.List;

public interface PagamentoExtraService {
    PagamentoExtraDTO salvar(PagamentoExtraDTO dto);
    PagamentoExtraDTO atualizar(Long id, PagamentoExtraDTO dto);
    void deletar(Long id);
    PagamentoExtraDTO buscarPorId(Long id);
    List<PagamentoExtraDTO> listarTodos();
}
