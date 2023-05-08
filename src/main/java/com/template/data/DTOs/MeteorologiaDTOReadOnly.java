package com.template.data.DTOs;

import com.template.data.entity.MeteorologiaEntity;

import java.time.LocalDate;
import java.util.List;

public record MeteorologiaDTOReadOnly(
        long id,
        String cidade,
        LocalDate data
) {
    public MeteorologiaDTOReadOnly(MeteorologiaEntity meteorologia) {
        this(meteorologia.getId(), meteorologia.getCidade(), meteorologia.getData());
    }
}
