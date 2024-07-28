package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Bebida")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Bebida extends ItemMenu{
    public static final String TABLE_NAME = "bebidas";
    
    @Column(name = "tamanho", nullable = false)
    @NotBlank
    private String tamanho;

    @Column(name = "quantidade", nullable = false)
    @NotNull
    private int quantidadeEstoque;
}
