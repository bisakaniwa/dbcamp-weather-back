package com.template.business.services;

import com.template.data.entity.MeteorologiaEntity;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MeteorologiaService {

    @Autowired
    MeteorologiaRepository meteorologiaRepository;

    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    public void excluirRegistro(long id) {
        meteorologiaRepository.deleteById(id);
    }
}
