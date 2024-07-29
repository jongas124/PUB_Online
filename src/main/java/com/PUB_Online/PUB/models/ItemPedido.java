package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "itens_pedido")
@Data
public class ItemPedido {

    public static final String TABLE_NAME = "itens_pedido";

    @Id
    @ManyToOne
    @JoinColumn(name = "itens_menu_id", nullable = false, referencedColumnName = "id")
    @NotNull
    public ItemMenu itemMenu;

    @Id
    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    @NotNull
    public Pedido pedido;

    @Column(name = "quantidade")
    public int quantidade;

}
