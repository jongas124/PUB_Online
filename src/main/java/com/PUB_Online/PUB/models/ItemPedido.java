package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @ManyToOne
    @JoinColumn(name = "item_menu_id")
    @NotNull
    public ItemMenu itemMenu;

    @Id
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    public Pedido pedido;

    @Column(name = "quantidade")
    public int quantidade;

}
