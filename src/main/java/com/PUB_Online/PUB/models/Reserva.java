package com.PUB_Online.PUB.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="reservas")
@Data
public class Reserva {
    public static final String TABLE_NAME = "reservas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", unique = true)
    private Long numero;
    
    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataReserva;

    @Column(name = "hora_reserva", nullable = false)
    private LocalTime horaAberturaReserva;

    @Column(name = "numero_pessoas", nullable = false)
    private Integer numeroPessoas;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_cpf", nullable = false, referencedColumnName = "cpf")
    @NotNull
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mesa_numero", nullable = false, referencedColumnName = "numero")
    @NotNull
    private Mesa mesa;
    
}


