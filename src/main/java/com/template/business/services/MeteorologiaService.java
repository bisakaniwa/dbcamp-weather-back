package com.template.business.services;

import com.template.data.entity.MeteorologiaEntity;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public MeteorologiaEntity atualizarRegistro(MeteorologiaEntity meteorologia) {
        Optional<MeteorologiaEntity> atualizacao = meteorologiaRepository.findById(meteorologia.getId());

        if (atualizacao.isPresent()) {
            atualizacao.get().setCidade(meteorologia.getCidade());
            atualizacao.get().setData(meteorologia.getData());
            atualizacao.get().setTempo(meteorologia.getTempo());
            atualizacao.get().setTurno(meteorologia.getTurno());
            atualizacao.get().setTemperaturaMaxima(meteorologia.getTemperaturaMaxima());
            atualizacao.get().setTemperaturaMinima(meteorologia.getTemperaturaMinima());
            atualizacao.get().setPrecipitacao(meteorologia.getPrecipitacao());
            atualizacao.get().setUmidade(meteorologia.getUmidade());
            atualizacao.get().setVelocidadeVentos(meteorologia.getVelocidadeVentos());
            return meteorologiaRepository.save(atualizacao.get());
        } else {
            return meteorologia;
        }
    }
}
