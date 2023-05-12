package com.template.service;

import com.template.business.services.MeteorologiaService;
import com.template.data.DTOs.MeteorologiaDTODadosLista;
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


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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

    @Mock
    ModelMapper mapperMock;

    @InjectMocks
    MeteorologiaService meteorologiaService;

    public MeteorologiaEntity mockRegistro() {
        return new MeteorologiaEntity(
                1230L, "Cidade1", LocalDate.of(2023, 4, 12), TempoDia.SOL, TempoNoite.NUBLADO,
                23f, 12f, 2f, 3f, 1f
        );
    }

    public MeteorologiaEntity outroRegistro() {
        return new MeteorologiaEntity(
                1231L, "Cidade2", LocalDate.of(2023, 4, 13), TempoDia.CHUVA, TempoNoite.LIMPO,
                24f, 15f, 0f, 1f, 4f
        );
    }

    public MeteorologiaEntity novoRegistro(String cidade, LocalDate data) {
        return new MeteorologiaEntity(1L, cidade, data, TempoDia.SOL, TempoNoite.LIMPO,
                2f, 2f, 2f, 2f, 2f);
    }

    public List<MeteorologiaEntity> previsaoSemanaCidade1() {
        MeteorologiaEntity dia1 = novoRegistro("Cidade1", LocalDate.now().plusDays(1));
        MeteorologiaEntity dia2 = novoRegistro("Cidade1", LocalDate.now().plusDays(2));
        MeteorologiaEntity dia3 = novoRegistro("Cidade1", LocalDate.now().plusDays(3));
        MeteorologiaEntity dia4 = novoRegistro("Cidade1", LocalDate.now().plusDays(4));
        MeteorologiaEntity dia5 = novoRegistro("Cidade1", LocalDate.now().plusDays(5));
        MeteorologiaEntity dia6 = novoRegistro("Cidade1", LocalDate.now().plusDays(6));

        return List.of(dia1, dia2, dia3, dia4, dia5, dia6);
    }

    @Test
    void buscarPaginaDeRegistrosComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();
        MeteorologiaEntity meteorologia2 = outroRegistro();
        MeteorologiaEntity meteorologia3 = new MeteorologiaEntity();

        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(meteorologia1, meteorologia2, meteorologia3);

        when(meteorologiaRepositoryMock.findAll()).thenReturn(listaMeteorologias);

        Page<MeteorologiaDTODadosLista> paginaMeteorologias = meteorologiaService.listarRegistros(paginacao);

        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(3, paginaMeteorologias.getTotalElements());

        verify(meteorologiaRepositoryMock).findAll();
    }

    @Test
    void bucarPaginaDeRegistrosVaziaELancarExcecao() {
        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(listaMeteorologias);

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.listarRegistros(paginacao));
        verify(meteorologiaRepositoryMock).findAll();
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();
        MeteorologiaEntity meteorologia2 = outroRegistro();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(List.of(meteorologia1, meteorologia2));

        List<MeteorologiaEntity> lista = meteorologiaService.listarTudo();

        Assertions.assertNotNull(lista);
        Assertions.assertEquals(2, lista.size());
        Assertions.assertEquals(MeteorologiaEntity.class, lista.get(0).getClass());

        Assertions.assertEquals(meteorologia1, lista.get(0));
        Assertions.assertEquals(meteorologia2, lista.get(1));
    }

    @Test
    void buscarPaginaDeRegistrosPorCidadeComSucesso() {
        MeteorologiaEntity cidade1 = mockRegistro();
        MeteorologiaEntity maisUmRegistroCidade1 = novoRegistro("Cidade1", LocalDate.now());

        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(cidade1, maisUmRegistroCidade1);

        when(meteorologiaRepositoryMock.findByCidade("Cidade1")).thenReturn(listaMeteorologias);

        Page<MeteorologiaDTODadosLista> paginaMeteorologias = meteorologiaService.listarPorCidade(paginacao, "Cidade1");

        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(2, paginaMeteorologias.getTotalElements());
        verify(meteorologiaRepositoryMock).findByCidade( "Cidade1");
    }

    @Test
    void buscarPaginaDeRegistrosDeCidadeNaoRegistradaELancarExcecao() {
        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of();

        when(meteorologiaRepositoryMock.findByCidade("Cidade1")).thenReturn(listaMeteorologias);

        Assertions.assertThrows(CidadeNotFoundException.class,
                () -> meteorologiaService.listarPorCidade(paginacao, "Cidade1"));
        verify(meteorologiaRepositoryMock).findByCidade("Cidade1");
    }

    @Test
    void buscarMeteorologiaDeHojeDaCidade1ComSucesso() {
        MeteorologiaEntity cidade1 = new MeteorologiaEntity(1234L, "Cidade1",
                LocalDate.now(), TempoDia.TEMPESTADE, TempoNoite.LIMPO, 26f,
                15f, 2f, 3f, 0f);

        when(meteorologiaRepositoryMock.findByCidade("Cidade1")).thenReturn(List.of(cidade1));

        MeteorologiaEntity tempoNaCidade1 = meteorologiaService.tempoHoje("Cidade1");

        Assertions.assertNotNull(tempoNaCidade1);
        Assertions.assertEquals(cidade1.getId(), tempoNaCidade1.getId());
        Assertions.assertEquals(cidade1.getData(), tempoNaCidade1.getData());
    }

    @Test
    void buscarMeteorologiaDeHojeParaCidadeNaoCadastradaEFalhar() {
        when(meteorologiaRepositoryMock.findByCidade("Cidade3")).thenReturn(List.of());

        Assertions.assertThrows(CidadeNotFoundException.class,
                () -> meteorologiaService.tempoHoje("Cidade3"));
        verify(meteorologiaRepositoryMock).findByCidade("Cidade3");
    }

    @Test
    void buscarRegistroNaoCadastradoParaHojeNaCidade1EFalhar() {
        MeteorologiaEntity cidade1 = mockRegistro();

        when(meteorologiaRepositoryMock.findByCidade("Cidade1")).thenReturn(List.of(cidade1));

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.tempoHoje("Cidade1"));
        verify(meteorologiaRepositoryMock).findByCidade("Cidade1");
    }

    @Test
    void buscarMeteorologiaDaSemanaDaCidade1ComSucesso() {
        List<MeteorologiaEntity> semana = previsaoSemanaCidade1();
        Pageable paginacao = PageRequest.of(0, 6);

        when(meteorologiaRepositoryMock.findByCidadeAndDataBetween("Cidade1", semana.get(0).getData(),
                semana.get(5).getData())).thenReturn(semana);

        Page<MeteorologiaEntity> metodoChamado = meteorologiaService.tempoSemana(paginacao, "Cidade1");

        Assertions.assertEquals(6, metodoChamado.getTotalElements());
        Assertions.assertEquals(6, metodoChamado.getSize());

        verify(meteorologiaRepositoryMock).findByCidadeAndDataBetween("Cidade1",
                semana.get(0).getData(), semana.get(5).getData());
    }

    @Test
    void buscarMeteorologiaDaSemanaDaCidade1ENaoEncontrarRegistros() {
        Pageable paginacao = PageRequest.of(0, 6);

        when(meteorologiaRepositoryMock.findByCidadeAndDataBetween("Cidade1", LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(6))).thenReturn(List.of());

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.tempoSemana(paginacao, "Cidade1"));
        verify(meteorologiaRepositoryMock).findByCidadeAndDataBetween("Cidade1",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(6));
    }

    @Test
    void registrarMeteorologiaComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();
        when(meteorologiaRepositoryMock.save(meteorologia1)).thenReturn(meteorologia1);

        Assertions.assertEquals(meteorologia1, meteorologiaService.novoRegistro(meteorologia1));
        verify(meteorologiaRepositoryMock).save(meteorologia1);
    }

    @Test
    void registrarMeteorologiaSemDataEFalhar() {
        MeteorologiaEntity meteorologiaSemData = novoRegistro("Townsville", null);

        try {
            meteorologiaRepositoryMock.save(meteorologiaSemData);
        } catch (Exception e) {
            Assertions.assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void atualizarMeteorologiaComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();
        meteorologia1.setData(LocalDate.of(2022, 7, 23));

        when(meteorologiaRepositoryMock.findById(meteorologia1.getId())).thenReturn(Optional.of(meteorologia1));
        when(meteorologiaRepositoryMock.save(meteorologia1)).thenReturn(meteorologia1);

        Assertions.assertNotNull(meteorologiaService.atualizarRegistro(meteorologia1));
        Assertions.assertEquals(meteorologia1, meteorologiaService.atualizarRegistro(meteorologia1));
    }

    @Test
    void atualizarMeteorologiaComUmCampoVazioComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();
        MeteorologiaEntity meteorologia2 = mockRegistro();
        meteorologia1.setTempoDia(null);

        when(meteorologiaRepositoryMock.findById(meteorologia2.getId())).thenReturn(Optional.of(meteorologia2));
        when(meteorologiaRepositoryMock.save(any())).thenReturn(meteorologia2);

        MeteorologiaEntity responseService = meteorologiaService.atualizarRegistro(meteorologia1);

        Assertions.assertEquals(responseService.getTempoDia(), meteorologia2.getTempoDia());
    }

    @Test
    void atualizarMeteorologiaInexistenteEFalhar() {
        when(meteorologiaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        verify(meteorologiaRepositoryMock, times(0)).save(mockRegistro());
        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.atualizarRegistro(mockRegistro()));
    }

    @Test
    void removerRegistroComSucesso() {
        MeteorologiaEntity meteorologia1 = mockRegistro();

        when(meteorologiaRepositoryMock.findById(1230L)).thenReturn(Optional.of(meteorologia1));

        meteorologiaService.excluirRegistro(1230);
        verify(meteorologiaRepositoryMock, times(1)).deleteById(meteorologia1.getId());
    }

    @Test
    void removerRegistroInexistente() {
        when(meteorologiaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.excluirRegistro(anyLong()));
    }
}
