package com.PUB_Online.PUB.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private DiasSemana diasSemana;

    @Column(name = "hora_abertura")
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento")
    private LocalTime horaFechamento;

    @AllArgsConstructor
    @Getter
    public enum DiasSemana {

        SEGUNDA(1, "Segunda"),
        TERCA(2, "Terça"),
        QUARTA(3, "Quarta"),
        QUINTA(4, "Quinta"),
        SEXTA(5, "Sexta"),
        SABADO(6, "Sábado"),
        DOMINGO(7, "Domingo");

        private int code;
        private String nomeDia; 

        public static DiasSemana fromCode(int code) {
            for (DiasSemana dia : DiasSemana.values()) {
                if (dia.code == code) {
                    return dia;
                }
            }
            return null;
        }
    }
}
