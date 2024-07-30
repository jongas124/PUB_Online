package com.PUB_Online.PUB.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name="pedidos")
@Data
public class Pedido {
    public static final String TABLE_NAME = "pedidos";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    
    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDate data;

    @CreationTimestamp
    @Column(name = "hora", updatable = false)
    private LocalTime hora;

    @Column(name = "preco", nullable = false) //considerar trocar float por double ou BigDecimal
    @NotNull
    private float preco;

    @Column(name = "status", nullable = false)
    @NotNull
    private Status status;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    private List<ItemPedido> itens = new ArrayList<ItemPedido>();

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", updatable = false, referencedColumnName = "cpf")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "comanda_numero", nullable = false, updatable = false, referencedColumnName = "numero")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Comanda comanda;
    
    @Getter
    @AllArgsConstructor
    public enum Status {
        FILA(1, "Fila de Espera"),
        PREPARANDO(2, "Preparando"),
        ENTREGUE(3, "Entregue"),;

        private int code;
        private String descricao;
    }

}
