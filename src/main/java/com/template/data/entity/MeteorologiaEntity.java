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
    @Column(name = "tempo_dia")
    @Enumerated(EnumType.STRING)
    private TempoDia tempoDia;

    @NotNull
    @Column(name = "tempo_noite")
    @Enumerated(EnumType.STRING)
    private TempoNoite tempoNoite;

    @NotNull
    @Column(name = "temperatura_maxima")
    private Float temperaturaMaxima;

    @NotNull
    @Column(name = "temperatura_minima")
    private Float temperaturaMinima;

    @NotNull
    private Float precipitacao;

    @NotNull
    private Float umidade;

    @NotNull
    @Column(name = "velocidade_ventos")
    private Float velocidadeVentos;

    public MeteorologiaEntity(long id, String cidade, LocalDate data, TempoDia tempoDia, TempoNoite tempoNoite,
                              Float temperaturaMaxima, Float temperaturaMinima, Float precipitacao, Float umidade,
                              Float velocidadeVentos) {
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

    public Float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(Float temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public Float getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(Float temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public Float getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(Float precipitacao) {
        this.precipitacao = precipitacao;
    }

    public Float getUmidade() {
        return umidade;
    }

    public void setUmidade(Float umidade) {
        this.umidade = umidade;
    }

    public Float getVelocidadeVentos() {
        return velocidadeVentos;
    }

    public void setVelocidadeVentos(Float velocidadeVentos) {
        this.velocidadeVentos = velocidadeVentos;
    }
}
