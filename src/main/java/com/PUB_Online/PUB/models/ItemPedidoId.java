package com.PUB_Online.PUB.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ItemPedidoId implements Serializable {
    
    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "itens_menu_id")
    private Long itemMenuId;
    
}
