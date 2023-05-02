package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import com.template.data.exception.MeteorologiaNotFoundException;
import com.template.data.repository.MeteorologiaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeteorologiaServiceTest {
    @Mock
    MeteorologiaRepository meteorologiaRepositoryMock;

    @InjectMocks
    MeteorologiaService meteorologiaService;

    public MeteorologiaEntity novaMeteorologia() {
        return new MeteorologiaEntity(
                1230L, "Cidade", LocalDate.of(2023, 4, 12), Tempo.SOL, Turno.DIA,
                23, 12, 2, 3, 1
        );
    }

    public MeteorologiaEntity outraMeteorologia() {
        return new MeteorologiaEntity(
                1231L, "Cidade2", LocalDate.of(2023, 4, 13), Tempo.CHUVA, Turno.NOITE,
                24, 15, 0, 1, 4
        );
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        MeteorologiaEntity dummyMeteorologia1 = outraMeteorologia();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(List.of(dummyMeteorologia, dummyMeteorologia1));

        List<MeteorologiaEntity> lista = meteorologiaService.listarTudo();

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

        assertThat(dummyMeteorologia).isEqualTo(meteorologiaService.novoRegistro(dummyMeteorologia));
    }

    @Test
    void registrarMeteorologiaSemDataEFalhar() {
        MeteorologiaEntity meteorologiaSemData = new MeteorologiaEntity(
                1231L, "Townsville", null, Tempo.CHUVA, Turno.NOITE, 22,
                11, 3, 2, 1);
        try {
            meteorologiaRepositoryMock.save(meteorologiaSemData);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void removerRegistroComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();

        meteorologiaService.excluirRegistro(1230);
        verify(meteorologiaRepositoryMock, times(1)).deleteById(1230L);
    }

    @Test
    void removerRegistroInexistente() {
        when(meteorologiaRepositoryMock.findById(anyLong()))
                .thenThrow(new MeteorologiaNotFoundException("Registro não encontrado."));

        try {
            meteorologiaService.excluirRegistro(1230L);
        } catch (Exception e) {
            assertEquals(MeteorologiaNotFoundException.class, e.getClass());
        }
    }
}
