package com.zse.FinIOAPI.service;

import java.util.List;

import com.zse.FinIOAPI.entity.dto.SaidaDTO;

public interface SaidaService {
    SaidaDTO salvar(SaidaDTO dto);
    SaidaDTO atualizar(Long id, SaidaDTO dto);
    void deletar(Long id);
    SaidaDTO buscarPorId(Long id);
    List<SaidaDTO> listarTodos();
}
