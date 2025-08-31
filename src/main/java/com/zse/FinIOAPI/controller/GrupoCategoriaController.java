package com.zse.FinIOAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zse.FinIOAPI.entity.dto.GrupoCategoriaDTO;
import com.zse.FinIOAPI.service.GrupoCategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grupo-categoria")
public class GrupoCategoriaController {

    @Autowired
    private GrupoCategoriaService service;

    
    @PostMapping
    @Operation(summary = "Insere novo Cupom!")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Cupom inserido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito ao inserir Cupom", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) 
    })
    public ResponseEntity<GrupoCategoriaDTO> create(@RequestBody @Valid GrupoCategoriaDTO dto) {
        try {
        	 return ResponseEntity.ok(service.salvar(dto));
        } catch (EntityNotFoundException e) {
           
        }
		return null; 
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
