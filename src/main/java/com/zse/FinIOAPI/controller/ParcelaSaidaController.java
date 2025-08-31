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

import com.zse.FinIOAPI.entity.dto.ParcelaSaidaDTO;
import com.zse.FinIOAPI.service.ParcelaSaidaService;

@RestController
@RequestMapping("/api/Parcelasaida")
public class ParcelaSaidaController {

    @Autowired
    private ParcelaSaidaService service;

    @PostMapping
    public ResponseEntity<ParcelaSaidaDTO> criar(@RequestBody ParcelaSaidaDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaSaidaDTO> atualizar(@PathVariable Long id, @RequestBody ParcelaSaidaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaSaidaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ParcelaSaidaDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}
