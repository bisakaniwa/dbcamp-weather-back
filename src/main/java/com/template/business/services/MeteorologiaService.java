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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        Page<MeteorologiaDTOReadOnly> buscar = meteorologiaRepository.findAll(paginacao)
                .map(MeteorologiaDTOReadOnly::new);
        if (buscar.isEmpty()) {
            throw new MeteorologiaNotFoundException("Não há registros.");
        } else {
            return buscar;
        }
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

        if (cidadeBuscada.isEmpty()) {
            throw new CidadeNotFoundException("Cidade não encontrada");
        } else {
            return cidadeBuscada.stream()
                    .filter(registros -> registros.getData().equals(hoje))
                    .findFirst()
                    .map(cidadeHoje -> criarMeteorologiaHoje(cidadeHoje))
                    .orElseThrow(() -> new MeteorologiaNotFoundException("Registro não encontrado."));
        }
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

    // Validar?
    public MeteorologiaEntity novoRegistro(MeteorologiaEntity meteorologia) {
        return meteorologiaRepository.save(meteorologia);
    }

    @Transactional
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
        Optional<MeteorologiaEntity> registro = meteorologiaRepository.findById(id);
        if (registro.isEmpty()) {
            throw new MeteorologiaNotFoundException("Registro não encontrado.");
        } else {
            meteorologiaRepository.deleteById(id);
        }
    }
}
