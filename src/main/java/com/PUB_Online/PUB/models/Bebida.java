package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("bebida")
@Data
@EqualsAndHashCode(callSuper=false)
public class Bebida extends ItemMenu{
    
    @Column(name = "tamanho", nullable = false)
    @NotBlank
    private String tamanho;

    @Column(name = "estoque", nullable = false)
    @NotNull
    private int quantidadeEstoque;
}
