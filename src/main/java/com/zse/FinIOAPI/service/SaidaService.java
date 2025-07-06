package com.zse.FinIOAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.dto.SaidaDTO;

@Service
public interface SaidaService {
    SaidaDTO salvar(SaidaDTO dto);
    SaidaDTO atualizar(Long id, SaidaDTO dto);
    void deletar(Long id);
    SaidaDTO buscarPorId(Long id);
    List<SaidaDTO> listarTodos();
}
