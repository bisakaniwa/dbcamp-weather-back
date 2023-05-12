package com.template.data.DTOs;

import com.template.data.entity.MeteorologiaEntity;

import java.time.LocalDate;

public record MeteorologiaDTODadosLista(
        long id,
        String cidade,
        LocalDate data
) {
    public MeteorologiaDTODadosLista(MeteorologiaEntity meteorologia) {
        this(meteorologia.getId(), meteorologia.getCidade(), meteorologia.getData());
    }
}
