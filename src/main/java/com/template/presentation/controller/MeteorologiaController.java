package com.template.presentation.controller;

import com.template.business.services.MeteorologiaService;
import com.template.data.DTOs.MeteorologiaDTOLista;
import com.template.data.entity.MeteorologiaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/meteorologia")
public class MeteorologiaController {

    @Autowired
    MeteorologiaService meteorologiaService;

    @GetMapping
    public ResponseEntity<Page<MeteorologiaDTOLista>> buscarRegistros(
            @PageableDefault(sort = {"data"}, direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(meteorologiaService.listarRegistros(paginacao));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MeteorologiaEntity> criarRegistro(@RequestBody MeteorologiaEntity meteorologia) {
        return new ResponseEntity<>(meteorologiaService.novoRegistro(meteorologia), HttpStatus.CREATED);
    }
}
