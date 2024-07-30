package com.PUB_Online.PUB.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="comandas")
@Data
public class Comanda {
    public static final String TABLE_NAME = "comandas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", unique = true)
    private Long numero;

    @Column(name = "valor_Total", nullable = false) //considerar trocar float por double ou BigDecimal
    @NotNull
    private float valorTotal;
    
    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDate data;

    @CreationTimestamp
    @Column(name = "hora_abertura_comanda", updatable = false)
    private LocalTime horaAberturaComanda;
    
    @Column(name = "hora_fechamento_comanda", updatable = false)
    private LocalTime horaFechamentoComanda;

    @OneToOne(mappedBy = "comanda")
    @NotNull
    // @JoinColumn(name = "mesa_numero", referencedColumnName = "numero", nullable = false)
    private Mesa mesa;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<Pedido>();

}


