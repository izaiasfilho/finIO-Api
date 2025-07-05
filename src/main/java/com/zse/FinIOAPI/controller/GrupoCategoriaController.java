package com.zse.FinIOAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;
import com.zse.FinIOAPI.service.GrupoCategoriaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/grupo-categoria")
public class GrupoCategoriaController {

    @Autowired
    private GrupoCategoriaService service;

    @PostMapping
    public ResponseEntity<GrupoCategoriaDTO> criar(@RequestBody GrupoCategoriaDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoCategoriaDTO> atualizar(@PathVariable Long id, @RequestBody GrupoCategoriaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoCategoriaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<GrupoCategoriaDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
