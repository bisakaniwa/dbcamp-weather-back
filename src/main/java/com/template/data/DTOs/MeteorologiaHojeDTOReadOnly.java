package com.template.data.DTOs;

import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.TempoDia;
import com.template.data.enumKind.TempoNoite;

import java.time.LocalDate;

public record MeteorologiaHojeDTOReadOnly(
        long id,
        String cidade,
        LocalDate data,
        TempoDia tempoDia,
        TempoNoite tempoNoite,
        float temperaturaMaxima,
        float temperaturaMinima,
        float precipitacao,
        float umidade,
        float velocidadeVentos
) {
    public MeteorologiaHojeDTOReadOnly(MeteorologiaEntity meteorologia) {
        this(meteorologia.getId(), meteorologia.getCidade(), meteorologia.getData(), meteorologia.getTempoDia(),
                meteorologia.getTempoNoite(), meteorologia.getTemperaturaMaxima(), meteorologia.getTemperaturaMinima(),
                meteorologia.getPrecipitacao(), meteorologia.getUmidade(), meteorologia.getVelocidadeVentos());
    }
}
