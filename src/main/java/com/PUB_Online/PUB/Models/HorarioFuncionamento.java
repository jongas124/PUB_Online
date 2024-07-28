package com.PUB_Online.PUB.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horario_funcionamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HorarioFuncionamento {
    
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

        DOMINGO(1),
        SEGUNDA(2),
        TERCA(3),
        QUARTA(4),
        QUINTA(5),
        SEXTA(6),
        SABADO(7);

        private int code;
    }
}
