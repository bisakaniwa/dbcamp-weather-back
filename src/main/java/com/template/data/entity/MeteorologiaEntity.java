package com.template.data.entity;

import com.template.data.enumKind.Tempo;
import com.template.data.enumKind.Turno;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "meteorologia")
public class MeteorologiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "tempo")
    private Tempo tempo;

    @Column(name = "turno")
    private Turno turno;

    @Column(name = "temperatura-maxima")
    private float temperaturaMaxima;

    @Column(name = "temperatura-minima")
    private float temperaturaMinima;

    @Column(name = "precipitacao")
    private float precipitacao;

    @Column(name = "umidade")
    private float umidade;

    @Column(name = "velocidade-ventos")
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
