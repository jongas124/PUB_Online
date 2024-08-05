package com.PUB_Online.PUB.controllers.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaCreateDTO(LocalDate data, LocalTime hora, Integer pessoas) {}
