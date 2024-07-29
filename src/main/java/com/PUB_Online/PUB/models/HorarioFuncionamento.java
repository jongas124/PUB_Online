package com.PUB_Online.PUB.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "horario_funcionamento")
@Data
public class HorarioFuncionamento {
    public static final String TABLE_NAME = "horario_funcionamento";
    
    @Id
    @Column(name = "dias_semana")
    private DiasSemana diasSemana;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    @AllArgsConstructor
    @Getter
    public enum DiasSemana {

        DOMINGO(1, "Domingo"),
        SEGUNDA(2, "Segunda"),
        TERCA(3, "Terça"),
        QUARTA(4, "Quarta"),
        QUINTA(5, "Quinta"),
        SEXTA(6, "Sexta"),
        SABADO(7, "Sábado");

        private int code;
        private String nomeDia; 
    }
}
