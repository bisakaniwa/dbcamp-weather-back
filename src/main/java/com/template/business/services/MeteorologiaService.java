package com.template.business.services;

import com.template.data.DTOs.MeteorologiaDTOReadOnly;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.exception.CidadeNotFoundException;
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

    public Page<MeteorologiaDTOReadOnly> listarRegistros(Pageable paginacao) {
        return meteorologiaRepository.findAll(paginacao).map(MeteorologiaDTOReadOnly::new);
    }

    public List<MeteorologiaEntity> listarTudo() {
        return meteorologiaRepository.findAll();
    }

    public Page<MeteorologiaDTOReadOnly> listarPorCidade(Pageable paginacao, String cidade) {
        Page<MeteorologiaDTOReadOnly> buscar = meteorologiaRepository.findByCidade(paginacao, cidade)
                .map(MeteorologiaDTOReadOnly::new);
        if (buscar.isEmpty()) {
            throw new CidadeNotFoundException("Cidade não encontrada.");
        } else {
            return buscar;
        }
    }

    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    public MeteorologiaEntity atualizarRegistro(MeteorologiaEntity meteorologia) {
        Optional<MeteorologiaEntity> atualizacao = meteorologiaRepository.findById(meteorologia.getId());

        if (atualizacao.isPresent()) {
            atualizacao.get().setCidade(meteorologia.getCidade());
            atualizacao.get().setData(meteorologia.getData());
            atualizacao.get().setTempoDia(meteorologia.getTempoDia());
            atualizacao.get().setTempoNoite(meteorologia.getTempoNoite());
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

    public void excluirRegistro(long id) {
        Optional<MeteorologiaEntity> meteorologia = meteorologiaRepository.findById(id);
        meteorologiaRepository.deleteById(id);
    }
}
