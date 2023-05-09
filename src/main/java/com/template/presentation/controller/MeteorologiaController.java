package com.template.presentation.controller;

import com.template.business.services.MeteorologiaService;
import com.template.data.DTOs.MeteorologiaDTOReadOnly;
import com.template.data.entity.MeteorologiaEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/meteorologia")
public class MeteorologiaController {

    MeteorologiaService meteorologiaService;

    public MeteorologiaController(MeteorologiaService meteorologiaService) {
        this.meteorologiaService = meteorologiaService;
    }

    @GetMapping
    public ResponseEntity<Page<MeteorologiaDTOReadOnly>> buscarRegistros(
            @PageableDefault(sort = {"data"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(meteorologiaService.listarRegistros(paginacao));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MeteorologiaEntity>> buscarTudo() {
        return ResponseEntity.ok(meteorologiaService.listarTudo());
    }

    @GetMapping("/{cidade}")
    public ResponseEntity<Page<MeteorologiaDTOReadOnly>> buscarPorCidade(
            @PageableDefault(sort = {"data"}, direction = Sort.Direction.DESC) Pageable paginacao,
            @PathVariable String cidade) {
        return ResponseEntity.ok(meteorologiaService.listarPorCidade(paginacao, cidade));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MeteorologiaEntity> criarRegistro(@RequestBody @Valid MeteorologiaEntity meteorologia) {
        return new ResponseEntity<>(meteorologiaService.novoRegistro(meteorologia), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<MeteorologiaEntity> atualizarRegistro(@RequestBody @Valid MeteorologiaEntity meteorologia) {
        return ResponseEntity.ok(meteorologiaService.atualizarRegistro(meteorologia));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<MeteorologiaEntity> excluirRegistro(@PathVariable long id) {
        try {
            meteorologiaService.excluirRegistro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
