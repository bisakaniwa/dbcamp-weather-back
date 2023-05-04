package com.template.business.services;

import com.template.data.DTOs.MeteorologiaDTOLista;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
public class MeteorologiaService {
    MeteorologiaRepository meteorologiaRepository;

    public MeteorologiaService(MeteorologiaRepository meteorologiaRepository) {
        this.meteorologiaRepository = meteorologiaRepository;
    }

    public Page<MeteorologiaDTOLista> listarRegistros(Pageable paginacao) {
        return meteorologiaRepository.findAll(paginacao).map(MeteorologiaDTOLista::new);
    }

    public List<MeteorologiaEntity> listarTudo() {
        return meteorologiaRepository.findAll();
    }

    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    public void excluirRegistro(long id) {
        Optional<MeteorologiaEntity> meteorologia = meteorologiaRepository.findById(id);
        meteorologiaRepository.deleteById(id);
    }
}
