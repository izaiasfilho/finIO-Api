package com.zse.FinIOAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.dto.ParcelaSaidaDTO;

@Service
public interface ParcelaSaidaService {
    ParcelaSaidaDTO salvar(ParcelaSaidaDTO dto);
    ParcelaSaidaDTO atualizar(Long id, ParcelaSaidaDTO dto);
    void deletar(Long id);
    ParcelaSaidaDTO buscarPorId(Long id);
    List<ParcelaSaidaDTO> listarTodos();
}
