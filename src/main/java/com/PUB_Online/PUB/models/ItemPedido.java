package com.PUB_Online.PUB.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "itens_pedido")
@Data
public class ItemPedido {

    public static final String TABLE_NAME = "itens_pedido";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Bebida bebida;

    @ManyToOne
    private Prato prato;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "preco_item_pedido")
    private BigDecimal precoItemPedido;

}
