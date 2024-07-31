package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    @EmbeddedId
    private ItemPedidoId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itens_menu_id", insertable = false, updatable = false, nullable = false, referencedColumnName = "id")
    @NotNull
    public ItemMenu itemMenu;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false, nullable = false, referencedColumnName = "id")
    @NotNull
    public Pedido pedido;

    @Column(name = "quantidade")
    public int quantidade;

}
