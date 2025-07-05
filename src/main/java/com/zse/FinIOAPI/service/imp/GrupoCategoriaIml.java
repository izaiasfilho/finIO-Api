package com.zse.FinIOAPI.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zse.FinIOAPI.entity.GrupoCategoria;
import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;
import com.zse.FinIOAPI.repository.GrupoCategoriaRepository;
import com.zse.FinIOAPI.service.GrupoCategoriaService;

@Service
public class GrupoCategoriaIml implements GrupoCategoriaService {

    @Autowired
    private GrupoCategoriaRepository repository;

    private GrupoCategoriaDTO toDTO(GrupoCategoria grupo) {
        return GrupoCategoriaDTO.builder()
                .id(grupo.getId())
                .descricao(grupo.getDescricao())
                .idTipoGrupo(grupo.getIdTipoGrupo())
                .ativo(grupo.getAtivo())
                .build();
    }

    private GrupoCategoria toEntity(GrupoCategoriaDTO dto) {
        return GrupoCategoria.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .idTipoGrupo(dto.getIdTipoGrupo())
                .ativo(dto.getAtivo())
                .build();
    }

    @Override
    public GrupoCategoriaDTO salvar(GrupoCategoriaDTO dto) {
        GrupoCategoria grupo = toEntity(dto);
        return toDTO(repository.save(grupo));
    }

    @Override
    public GrupoCategoriaDTO atualizar(Long id, GrupoCategoriaDTO dto) {
        GrupoCategoria existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GrupoCategoria não encontrado"));

        existente.setDescricao(dto.getDescricao());
        existente.setIdTipoGrupo(dto.getIdTipoGrupo());
        existente.setAtivo(dto.getAtivo());

        return toDTO(repository.save(existente));
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GrupoCategoriaDTO buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("GrupoCategoria não encontrado"));
    }

    @Override
    public List<GrupoCategoriaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
