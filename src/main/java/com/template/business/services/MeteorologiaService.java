package com.template.business.services;

import com.template.data.DTOs.MeteorologiaDTOReadOnly;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.exception.CidadeNotFoundException;
import com.template.data.exception.MeteorologiaNotFoundException;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

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
        Optional<MeteorologiaEntity> registroBuscado = meteorologiaRepository.findById(meteorologia.getId());

        if (registroBuscado.isEmpty()) {
            throw new MeteorologiaNotFoundException("Registro não encontrado");
        } else {
            MeteorologiaEntity registroAtualizado = registroBuscado.get();
            registroAtualizado.setCidade(requireNonNullElse(meteorologia.getCidade(), registroAtualizado.getCidade()));
            registroAtualizado.setData(requireNonNullElse(meteorologia.getData(), registroAtualizado.getData()));
            registroAtualizado.setTempoDia(requireNonNullElse(meteorologia.getTempoDia(), registroAtualizado.getTempoDia()));
            registroAtualizado.setTempoNoite(requireNonNullElse(meteorologia.getTempoNoite(), registroAtualizado.getTempoNoite()));
            registroAtualizado.setTemperaturaMaxima(requireNonNullElse(meteorologia.getTemperaturaMaxima(),
                    registroAtualizado.getTemperaturaMaxima()));
            registroAtualizado.setTemperaturaMinima(requireNonNullElse(meteorologia.getTemperaturaMinima(),
                    registroAtualizado.getTemperaturaMinima()));
            registroAtualizado.setPrecipitacao(requireNonNullElse(meteorologia.getPrecipitacao(), registroAtualizado.getPrecipitacao()));
            registroAtualizado.setUmidade(requireNonNullElse(meteorologia.getUmidade(), registroAtualizado.getUmidade()));
            registroAtualizado.setVelocidadeVentos(requireNonNullElse(meteorologia.getVelocidadeVentos(),
                    registroAtualizado.getVelocidadeVentos()));
            return meteorologiaRepository.save(registroAtualizado);
        }
    }

    public void excluirRegistro(long id) {
        Optional<MeteorologiaEntity> meteorologia = meteorologiaRepository.findById(id);
        meteorologiaRepository.deleteById(id);
    }
}
