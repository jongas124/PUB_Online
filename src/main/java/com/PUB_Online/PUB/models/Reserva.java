package com.PUB_Online.PUB.models;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="reservas")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    public static final String TABLE_NAME = "reservas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", unique = true)
    private Long numero;
    
    @Column(name = "dataReserva")
    private LocalDate data;

    @Column(name = "horaReserva")
    private LocalTime horaAberturaReser;

    @Column(name = "cpfCliente", nullable = false)
    @NotNull
    private Cliente cliente;

}


