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

@Table(name="comandas")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comanda {
    public static final String TABLE_NAME = "comandas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", unique = true)
    private Long numero;
    
    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDate data;

    @CreationTimestamp
    @Column(name = "horaAberturaComanda", updatable = false)
    private LocalTime horaAberturaComanda;
    
    @Column(name = "horaFechamentoComanda")
    private LocalTime horaFechamentoComanda;

    @Column(name = "valorTotal", nullable = false) //considerar trocar float por double ou BigDecimal
    @NotNull
    private float valorTotal;

}


