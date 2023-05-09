package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.DTOs.MeteorologiaDTOReadOnly;
import com.template.data.entity.MeteorologiaEntity;
import com.template.data.enumKind.TempoDia;
import com.template.data.enumKind.TempoNoite;
import com.template.data.exception.CidadeNotFoundException;
import com.template.data.exception.MeteorologiaNotFoundException;
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
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MeteorologiaServiceTest {
    @Mock
    MeteorologiaRepository meteorologiaRepositoryMock;

    @InjectMocks
    MeteorologiaService meteorologiaService;

    public MeteorologiaEntity novaMeteorologia() {
        return new MeteorologiaEntity(
                1230L, "Cidade1", LocalDate.of(2023, 4, 12), TempoDia.SOL, TempoNoite.NUBLADO,
                23f, 12f, 2f, 3f, 1f
        );
    }

    public MeteorologiaEntity outraMeteorologia() {
        return new MeteorologiaEntity(
                1231L, "Cidade2", LocalDate.of(2023, 4, 13), TempoDia.CHUVA, TempoNoite.LIMPO,
                24f, 15f, 0f, 1f, 4f
        );
    }

    @Test
    void buscarPaginaDeRegistrosComSucesso() {
        MeteorologiaEntity item01 = novaMeteorologia();
        MeteorologiaEntity item02 = outraMeteorologia();
        MeteorologiaEntity item03 = new MeteorologiaEntity();

        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(item01, item02, item03);
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findAll(paginacao)).thenReturn(pagina);

        Page<MeteorologiaDTOReadOnly> paginaMeteorologias = meteorologiaService.listarRegistros(paginacao);

        Assertions.assertNotNull(listaMeteorologias);
        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(3, paginaMeteorologias.getTotalElements());
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity dummyMeteorologia = novaMeteorologia();
        MeteorologiaEntity dummyMeteorologia1 = outraMeteorologia();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(List.of(dummyMeteorologia, dummyMeteorologia1));

        List<MeteorologiaEntity> lista = meteorologiaService.listarTudo();

        Assertions.assertNotNull(lista);
        Assertions.assertEquals(2, lista.size());
        Assertions.assertEquals(MeteorologiaEntity.class, lista.get(0).getClass());

        Assertions.assertEquals(dummyMeteorologia, lista.get(0));
        Assertions.assertEquals(dummyMeteorologia1, lista.get(1));
    }

    @Test
    void buscarPorCidadeComSucesso() {
        MeteorologiaEntity cidade1 = novaMeteorologia();
        MeteorologiaEntity maisUmRegistroCidade1 = new MeteorologiaEntity(1234L, "Cidade1",
                LocalDate.of(2022, 5, 3), TempoDia.TEMPESTADE, TempoNoite.LIMPO, 26f,
                15f, 2f, 3f, 0f);

        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(cidade1, maisUmRegistroCidade1);
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findByCidade(paginacao, "Cidade1")).thenReturn(pagina);

        Page<MeteorologiaDTOReadOnly> paginaMeteorologias = meteorologiaService.listarPorCidade(paginacao, "Cidade1");

        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(2, paginaMeteorologias.getTotalElements());
        verify(meteorologiaRepositoryMock, times(1)).findByCidade(paginacao, "Cidade1");
    }

    @Test
    void buscarUmaCidadeNaoRegistrada() {
        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of();
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findByCidade(paginacao, "Cidade1")).thenReturn(pagina);

        Assertions.assertThrows(CidadeNotFoundException.class,
                () -> meteorologiaService.listarPorCidade(paginacao, "Cidade1"));
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
                1231L, "Townsville", null, TempoDia.CHUVA, TempoNoite.CHUVA, 22f,
                11f, 3f, 2f, 1f);
        try {
            meteorologiaRepositoryMock.save(meteorologiaSemData);
        } catch (Exception e) {
            Assertions.assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void atualizarMeteorologiaCompletaComSucesso() {
        MeteorologiaEntity meteorologia1 = novaMeteorologia();
        meteorologia1.setData(LocalDate.of(2022, 7, 23));

        when(meteorologiaRepositoryMock.findById(meteorologia1.getId())).thenReturn(Optional.of(meteorologia1));
        when(meteorologiaRepositoryMock.save(meteorologia1)).thenReturn(meteorologia1);

        Assertions.assertNotNull(meteorologiaService.atualizarRegistro(meteorologia1));
        Assertions.assertEquals(meteorologia1, meteorologiaService.atualizarRegistro(meteorologia1));
    }

    @Test
    void atualizarMeteorologiaComUmCampoVazioComSucesso() {
        MeteorologiaEntity meteorologia1 = novaMeteorologia();
        MeteorologiaEntity meteorologia2 = novaMeteorologia();
        meteorologia1.setTempoDia(null);

        when(meteorologiaRepositoryMock.findById(meteorologia2.getId())).thenReturn(Optional.of(meteorologia2));
        when(meteorologiaRepositoryMock.save(any())).thenReturn(meteorologia2);

        var responseService = meteorologiaService.atualizarRegistro(meteorologia1);

        Assertions.assertEquals(responseService.getTempoDia(), meteorologia2.getTempoDia() );

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
            Assertions.assertEquals(MeteorologiaNotFoundException.class, e.getClass());
        }
    }
}
