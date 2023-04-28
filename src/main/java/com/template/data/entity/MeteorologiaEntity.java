package com.template.data.entity;

import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "meteorologia")
public class MeteorologiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotBlank
    private long id;

    @Column(name = "cidade")
    @NotBlank
    private String cidade;

    @Column(name = "data")
    @NotBlank
    private LocalDate data;

    @Column(name = "tempo")
    @NotBlank
    private Tempo tempo;

    @Column(name = "turno")
    @NotBlank
    private Turno turno;

    @Column(name = "temperatura-maxima")
    @NotBlank
    private float temperaturaMaxima;

    @Column(name = "temperatura-minima")
    @NotBlank
    private float temperaturaMinima;

    @Column(name = "precipitacao")
    @NotBlank
    private float precipitacao;

    @Column(name = "umidade")
    @NotBlank
    private float umidade;

    @Column(name = "velocidade-ventos")
    @NotBlank
    private float velocidadeVentos;

    public MeteorologiaEntity(long id, String cidade, LocalDate data, Tempo tempo, Turno turno, float temperaturaMaxima,
                              float temperaturaMinima, float precipitacao, float umidade, float velocidadeVentos) {
        this.id = id;
        this.cidade = cidade;
        this.data = data;
        this.tempo = tempo;
        this.turno = turno;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.precipitacao = precipitacao;
        this.umidade = umidade;
        this.velocidadeVentos = velocidadeVentos;
    }

    public MeteorologiaEntity() {
    }

    public MeteorologiaEntity(long l, String cidade2, Date date) {
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

    public Tempo getTempo() {
        return tempo;
    }

    public void setTempo(Tempo tempo) {
        this.tempo = tempo;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
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
