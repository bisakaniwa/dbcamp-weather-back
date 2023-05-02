package com.template.data.DTOs;

import com.template.data.entity.MeteorologiaEntity;

import java.time.LocalDate;

public record MeteorologiaDTOLista(
        String cidade,
        LocalDate data
) {
    public MeteorologiaDTOLista(MeteorologiaEntity meteorologia) {
        this(meteorologia.getCidade(), meteorologia.getData());
    }
}
