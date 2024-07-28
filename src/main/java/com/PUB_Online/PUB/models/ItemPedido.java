package com.PUB_Online.PUB.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemPedido {
    @Id
    public ItemMenu itemMenu;

    @Id
    public Pedido pedido;

    public int quantidade;
}
