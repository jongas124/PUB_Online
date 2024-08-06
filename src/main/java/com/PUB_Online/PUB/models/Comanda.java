package com.PUB_Online.PUB.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comandas")
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comanda {
    public static final String TABLE_NAME = "comandas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", unique = true)
    private Long numero;

    @Column(name = "valor_Total", nullable = false) //considerar trocar float por double ou BigDecimal
    @NotNull
    private BigDecimal valorTotal;
    
    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDate data;

    @CreationTimestamp
    @Column(name = "hora_abertura_comanda", updatable = false)
    private LocalTime horaAberturaComanda;
    
    @Column(name = "hora_fechamento_comanda", updatable = false)
    private LocalTime horaFechamentoComanda;

    @OneToOne(mappedBy = "comanda")
    @JoinColumn(name = "mesa_numero", referencedColumnName = "numero")
    private Mesa mesa;

    @OneToOne(mappedBy = "comanda")
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cpf")
    private Cliente cliente;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<Pedido>();

}


