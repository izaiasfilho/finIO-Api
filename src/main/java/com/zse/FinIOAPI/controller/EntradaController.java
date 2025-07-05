package com.zse.FinIOAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zse.FinIOAPI.entity.dto.EntradaDTO;
import com.zse.FinIOAPI.service.EntradaService;

@RestController
@RequestMapping("/api/entrada")
public class EntradaController {

    @Autowired
    private EntradaService service;

    @PostMapping
    public ResponseEntity<EntradaDTO> criar(@RequestBody EntradaDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> atualizar(@PathVariable Long id, @RequestBody EntradaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}

