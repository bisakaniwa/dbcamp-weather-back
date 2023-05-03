package com.template.presentation.controller;

import com.template.business.services.MeteorologiaService;
import com.template.data.entity.MeteorologiaEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MeteorologiaEntity>> buscarTodosRegistros() {
        return ResponseEntity.ok(meteorologiaService.listarTudo());
    }

    @PostMapping
    public ResponseEntity<MeteorologiaEntity> criarRegistro(@RequestBody MeteorologiaEntity meteorologia) {
        return new ResponseEntity<>(meteorologiaService.novoRegistro(meteorologia), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
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
