package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import com.template.data.repository.MeteorologiaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    public MeteorologiaEntity outraMeteorologia() {
        return new MeteorologiaEntity(
                1231L, "Cidade2", new Date(2023, Calendar.JUNE, 13), Tempo.CHUVA, Turno.NOITE,
                24, 15, 0, 1, 4
        );
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        MeteorologiaEntity dummyMeteorologia1 = outraMeteorologia();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(List.of(dummyMeteorologia, dummyMeteorologia1));

        List<MeteorologiaEntity> lista = meteorologiaServiceMock.listarTudo();

        assertNotNull(lista);
        assertEquals(2, lista.size());
        assertEquals(MeteorologiaEntity.class, lista.get(0).getClass());

        assertEquals(dummyMeteorologia, lista.get(0));
        assertEquals(dummyMeteorologia1, lista.get(1));
    }

    @Test
    void registrarMeteorologiaComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        when(meteorologiaRepositoryMock.save(dummyMeteorologia)).thenReturn(dummyMeteorologia);

        assertThat(dummyMeteorologia).isEqualTo(meteorologiaServiceMock.novoRegistro(dummyMeteorologia));
    }

}
