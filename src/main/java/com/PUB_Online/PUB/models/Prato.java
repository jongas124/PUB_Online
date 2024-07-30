package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Entity
@DiscriminatorValue("prato")
@Data
@EqualsAndHashCode(callSuper = false)
public class Prato extends ItemMenu {

    @Column(name = "descricao")
    @Size(min = 10, max = 250)
    private String descricao;

    @Column(name = "disponivel", nullable = false)
    @NotNull
    private boolean disponivel;

    @Column(name = "categoria", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @Getter
    @AllArgsConstructor
    public enum Categoria {
        ENTRADA(1, "Entrada"),
        PRATOPRINCIPAL(2, "Prato Principal"),
        SOBREMESA(3, "Sobremesa"),
        DRINK(4, "Drink");

        private int code;
        private String nome;
    }
    
}
