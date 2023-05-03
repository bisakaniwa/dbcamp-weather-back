package com.template.data.entity;

import com.template.data.enumKind.TempoDia;
import com.template.data.enumKind.TempoNoite;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "meteorologia")
public class MeteorologiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String cidade;

    @NotNull
    private LocalDate data;

    @NotNull
    private TempoDia tempoDia;

    @NotNull
    private TempoNoite tempoNoite;

    @NotNull
    private float temperaturaMaxima;

    @NotNull
    private float temperaturaMinima;

    @NotNull
    private float precipitacao;

    @NotNull
    private float umidade;

    @NotNull
    private float velocidadeVentos;

    public MeteorologiaEntity(long id, String cidade, LocalDate data, TempoDia tempoDia, TempoNoite tempoNoite,
                              float temperaturaMaxima, float temperaturaMinima, float precipitacao, float umidade,
                              float velocidadeVentos) {
        this.id = id;
        this.cidade = cidade;
        this.data = data;
        this.tempoDia = tempoDia;
        this.tempoNoite = tempoNoite;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.precipitacao = precipitacao;
        this.umidade = umidade;
        this.velocidadeVentos = velocidadeVentos;
    }

    public MeteorologiaEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TempoDia getTempoDia() {
        return tempoDia;
    }

    public void setTempoDia(TempoDia tempoDia) {
        this.tempoDia = tempoDia;
    }

    public TempoNoite getTempoNoite() {
        return tempoNoite;
    }

    public void setTempoNoite(TempoNoite tempoNoite) {
        this.tempoNoite = tempoNoite;
    }

    public float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(float temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public float getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(float temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public float getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(float precipitacao) {
        this.precipitacao = precipitacao;
    }

    public float getUmidade() {
        return umidade;
    }

    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    public float getVelocidadeVentos() {
        return velocidadeVentos;
    }

    public void setVelocidadeVentos(float velocidadeVentos) {
        this.velocidadeVentos = velocidadeVentos;
    }
}
