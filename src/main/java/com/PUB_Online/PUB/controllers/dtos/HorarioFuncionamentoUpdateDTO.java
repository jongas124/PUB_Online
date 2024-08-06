package com.PUB_Online.PUB.controllers.dtos;

import java.time.LocalTime;

public record HorarioFuncionamentoUpdateDTO(LocalTime horaAbertura, LocalTime horaFechamento) {}