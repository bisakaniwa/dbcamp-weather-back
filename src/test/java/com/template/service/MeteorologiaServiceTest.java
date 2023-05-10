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
        MeteorologiaEntity meteorologia1 = novaMeteorologia();
        MeteorologiaEntity meteorologia2 = outraMeteorologia();
        MeteorologiaEntity meteorologia3 = new MeteorologiaEntity();

        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of(meteorologia1, meteorologia2, meteorologia3);
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findAll(paginacao)).thenReturn(pagina);

        Page<MeteorologiaDTODadosLista> paginaMeteorologias = meteorologiaService.listarRegistros(paginacao);

        Assertions.assertNotNull(listaMeteorologias);
        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(3, paginaMeteorologias.getTotalElements());
    }

    @Test
    void bucarPaginaDeRegistrosVaziaELancarExcecao() {
        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of();
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findAll(paginacao)).thenReturn(pagina);

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.listarRegistros(paginacao));
    }

    @Test
    void buscarTodosOsRegistrosComSucesso() {
        MeteorologiaEntity meteorologia1 = novaMeteorologia();
        MeteorologiaEntity meteorologia2 = outraMeteorologia();

        when(meteorologiaRepositoryMock.findAll()).thenReturn(List.of(meteorologia1, meteorologia2));

        List<MeteorologiaEntity> lista = meteorologiaService.listarTudo();

        Assertions.assertNotNull(lista);
        Assertions.assertEquals(2, lista.size());
        Assertions.assertEquals(MeteorologiaEntity.class, lista.get(0).getClass());

        Assertions.assertEquals(meteorologia1, lista.get(0));
        Assertions.assertEquals(meteorologia2, lista.get(1));
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

        Page<MeteorologiaDTODadosLista> paginaMeteorologias = meteorologiaService.listarPorCidade(paginacao, "Cidade1");

        Assertions.assertNotNull(paginaMeteorologias);
        Assertions.assertEquals(2, paginaMeteorologias.getTotalElements());
        verify(meteorologiaRepositoryMock, times(1)).findByCidade(paginacao, "Cidade1");
    }

    @Test
    void buscarUmaCidadeNaoRegistradaPaginado() {
        Pageable paginacao = PageRequest.of(0, 10);
        List<MeteorologiaEntity> listaMeteorologias = List.of();
        Page<MeteorologiaEntity> pagina = new PageImpl<>(listaMeteorologias, paginacao, 1);

        when(meteorologiaRepositoryMock.findByCidade(paginacao, "Cidade1")).thenReturn(pagina);

        Assertions.assertThrows(CidadeNotFoundException.class,
                () -> meteorologiaService.listarPorCidade(paginacao, "Cidade1"));
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
    void buscarMeteorologiaDeHojeParaCidadeNaoCadastrada() {
        when(meteorologiaRepositoryMock.findByCidade("Cidade3")).thenReturn(List.of());

        Assertions.assertThrows(CidadeNotFoundException.class,
                () -> meteorologiaService.tempoHoje("Cidade3"));
    }

    @Test
    void buscarRegistroNaoCadastradoParaHojeNaCidade1() {
        MeteorologiaEntity cidade1 = novaMeteorologia();

        when(meteorologiaRepositoryMock.findByCidade("Cidade1")).thenReturn(List.of(cidade1));

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.tempoHoje("Cidade1"));
    }

    @Test
    void registrarMeteorologiaComSucesso() {
        MeteorologiaEntity meteorologia1 = novaMeteorologia();
        when(meteorologiaRepositoryMock.save(meteorologia1)).thenReturn(meteorologia1);

        Assertions.assertEquals(meteorologia1, meteorologiaService.novoRegistro(meteorologia1));
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

        MeteorologiaEntity responseService = meteorologiaService.atualizarRegistro(meteorologia1);

        Assertions.assertEquals(responseService.getTempoDia(), meteorologia2.getTempoDia());
    }

    @Test
    void atualizarMeteorologiaInexistenteEFalhar() {
        when(meteorologiaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        verify(meteorologiaRepositoryMock, times(0)).save(novaMeteorologia());
        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.atualizarRegistro(novaMeteorologia()));
    }

    @Test
    void removerRegistroComSucesso() {
        MeteorologiaEntity meteorologia1 = novaMeteorologia();

        when(meteorologiaRepositoryMock.findById(1230L)).thenReturn(Optional.of(meteorologia1));

        meteorologiaService.excluirRegistro(1230);
        verify(meteorologiaRepositoryMock, times(1)).deleteById(1230L);
    }

    @Test
    void removerRegistroInexistente() {
        when(meteorologiaRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(MeteorologiaNotFoundException.class,
                () -> meteorologiaService.excluirRegistro(anyLong()));
    }
}
