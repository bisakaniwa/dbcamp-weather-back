package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import com.template.data.repository.MeteorologiaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;


import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class MeteorologiaServiceTest {
    @Mock
    MeteorologiaRepository meteorologiaRepositoryMock;

    @InjectMocks
    MeteorologiaService meteorologiaServiceMock;

    public MeteorologiaEntity novaMeteorologia() {
        return new MeteorologiaEntity(
                1230L, "Cidade", new Date(2023, Calendar.MAY, 12), Tempo.SOL, Turno.DIA,
                23, 12, 2, 3, 1
        );
    }

    @Test
    void registrarMeteorologiaComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        when(meteorologiaRepositoryMock.save(dummyMeteorologia)).thenReturn(dummyMeteorologia);

        assertThat(dummyMeteorologia).isEqualTo(meteorologiaServiceMock.novoRegistro(dummyMeteorologia));
    }

}
