package com.PUB_Online.PUB.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    
    @CreationTimestamp //Conferir Depois
    @Column(name = "data", updatable = false)
    private LocalDate data;

    @CreationTimestamp //Conferir Depois
    @Column(name = "hora", updatable = false)
    private LocalTime hora;

    @Column(name = "preco", nullable = false) //considerar trocar float por double ou BigDecimal
    @NotNull
    private float preco;

    @Column(name = "comanda", nullable = false) //Era pra preparar o link pra comanda, mas não acho q carregar ela seja uma boa
    @NotNull
    private int numeroComanda;

    @Column(name = "status", nullable = false)
    @NotNull
    private Status status;
    
    @Getter
    @AllArgsConstructor
    public enum Status {
        FILA(1, "Fila de Espera"),
        PREPARANDO(2, "Preparando"),
        ENTREGUE(3, "Entregue"),;

        private int code;
        private String descricao;
    }

    @OneToMany(mappedBy = "itemMenu")
    @CollectionTable(name = "PedidoItens")
    @Column(name = "item")
    private List<ItemPedido> itens;
}
