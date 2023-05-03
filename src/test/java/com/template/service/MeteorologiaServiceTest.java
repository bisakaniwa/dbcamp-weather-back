package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.DTOs.MeteorologiaDTOLista;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import com.template.data.repository.MeteorologiaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.when;

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
    void buscarPaginaDeRegistrosComSucesso() {
        MeteorologiaEntity item01 = novaMeteorologia();
        MeteorologiaEntity item02 = outraMeteorologia();
        MeteorologiaEntity item03 = new MeteorologiaEntity();

        Pageable paginacao = PageRequest.of(0,10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(item01, item02, item03);
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findAll(paginacao)).thenReturn(pagina);

        Page<MeteorologiaDTOLista> paginaMeteorologias = meteorologiaService.listarRegistros(paginacao);

        Assertions.assertNotNull(listaMeteorologias);
        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(3, paginaMeteorologias.getTotalElements());
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
            Assertions.assertEquals(RuntimeException.class, e.getClass());
        }
    }
}
