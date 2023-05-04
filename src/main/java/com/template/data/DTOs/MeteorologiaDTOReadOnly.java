package com.template.data.DTOs;

import com.template.data.entity.MeteorologiaEntity;

import java.time.LocalDate;

public record MeteorologiaDTOReadOnly(
        String cidade,
        LocalDate data
) {
    public MeteorologiaDTOReadOnly(MeteorologiaEntity meteorologia) {
        this(meteorologia.getCidade(), meteorologia.getData());
    }
}
