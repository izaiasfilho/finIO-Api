package com.zse.FinIOAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;

@Service
public interface GrupoCategoriaService {
    GrupoCategoriaDTO salvar(GrupoCategoriaDTO dto);
    GrupoCategoriaDTO atualizar(Long id, GrupoCategoriaDTO dto);
    void deletar(Long id);
    GrupoCategoriaDTO buscarPorId(Long id);
    List<GrupoCategoriaDTO> listarTodos();
}
