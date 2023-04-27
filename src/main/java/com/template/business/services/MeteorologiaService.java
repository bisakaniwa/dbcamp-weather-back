package com.template.business.services;

import com.template.data.entity.MeteorologiaEntity;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MeteorologiaService {

    @Autowired
    MeteorologiaRepository meteorologiaRepository;

    public List<MeteorologiaEntity> listarTudo() {
        return meteorologiaRepository.findAll();
    }
    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }
}
