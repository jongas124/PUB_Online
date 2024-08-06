package com.PUB_Online.PUB.controllers.dtos;

import java.time.LocalTime;

import com.PUB_Online.PUB.models.HorarioFuncionamento.DiasSemana;

public record HorarioFuncionamentoCreateDTO(DiasSemana diasSemana, LocalTime horaAbertura, LocalTime horaFechamento) {}