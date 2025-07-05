package com.zse.FinIOAPI.service;

import com.zse.FinIOAPI.entity.dto.EntradaDTO;

import java.util.List;

public interface EntradaService {
    EntradaDTO salvar(EntradaDTO dto);
    EntradaDTO atualizar(Long id, EntradaDTO dto);
    void deletar(Long id);
    EntradaDTO buscarPorId(Long id);
    List<EntradaDTO> listarTodos();
}
