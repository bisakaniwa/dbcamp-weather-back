package com.template.business.services;

import com.template.data.entity.MeteorologiaEntity;
import com.template.data.exception.MeteorologiaNotFoundException;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class MeteorologiaService {

    @Autowired
    MeteorologiaRepository meteorologiaRepository;

    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    public void excluirRegistro(long id) {
        Optional<MeteorologiaEntity> meteorologia = meteorologiaRepository.findById(id);
        if (meteorologia.isPresent()) {
            meteorologiaRepository.deleteById(id);
        } else {
            throw new MeteorologiaNotFoundException("Para de inventar moda.");
        }
    }
}
