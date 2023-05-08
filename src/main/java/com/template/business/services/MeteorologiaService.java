package com.template.business.services;

import com.template.data.DTOs.MeteorologiaDTOReadOnly;
import com.template.data.DTOs.MeteorologiaHojeDTOReadOnly;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.exception.CidadeNotFoundException;
import com.template.data.exception.MeteorologiaNotFoundException;
import com.template.data.repository.MeteorologiaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public MeteorologiaHojeDTOReadOnly tempoHoje(String cidade) {
        LocalDate hoje = LocalDate.now();
        List<MeteorologiaEntity> cidadeBuscada = meteorologiaRepository.findByCidade(cidade);

        return cidadeBuscada.stream()
                .filter(registros -> registros.getData().equals(hoje))
                .findFirst()
                .map(cidadeHoje -> criarMeteorologiaHoje(cidadeHoje))
                .orElseThrow(() -> new MeteorologiaNotFoundException("Registro não encontrado."));
    }

    private static MeteorologiaHojeDTOReadOnly criarMeteorologiaHoje(MeteorologiaEntity cidadeHoje) {
        return new MeteorologiaHojeDTOReadOnly(
                cidadeHoje.getId(),
                cidadeHoje.getCidade(),
                cidadeHoje.getData(),
                cidadeHoje.getTempoDia(),
                cidadeHoje.getTempoNoite(),
                cidadeHoje.getTemperaturaMaxima(),
                cidadeHoje.getTemperaturaMinima(),
                cidadeHoje.getPrecipitacao(),
                cidadeHoje.getUmidade(),
                cidadeHoje.getVelocidadeVentos());
    }

    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    public void excluirRegistro(long id) {
        Optional<MeteorologiaEntity> meteorologia = meteorologiaRepository.findById(id);
        meteorologiaRepository.deleteById(id);
    }
}
