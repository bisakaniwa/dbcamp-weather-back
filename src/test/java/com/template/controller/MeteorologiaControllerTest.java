package com.template.controller;

import com.template.business.services.MeteorologiaService;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import com.template.presentation.controller.MeteorologiaController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class MeteorologiaControllerTest {
    @MockBean
    MeteorologiaService meteorologiaServiceMock;

    @InjectMocks
    MeteorologiaController meteorologiaControllerMock;

    public MeteorologiaEntity novaMeteorologia() {
        return new MeteorologiaEntity(
                1230L, "Cidade", new Date(2023, Calendar.MAY, 12), Tempo.SOL, Turno.DIA,
                23, 12, 2, 3, 1
        );
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        MeteorologiaEntity dummyMeteorologia1 = new MeteorologiaEntity(
                1231L, "Cidade2", new Date(2023, Calendar.JUNE, 13), Tempo.CHUVA, Turno.NOITE,
                24, 15, 0, 1, 4
        );
    }

    @Test
    void registrarMeteorologiaComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        when(meteorologiaServiceMock.novoRegistro(dummyMeteorologia)).thenReturn(dummyMeteorologia);


    }
}
