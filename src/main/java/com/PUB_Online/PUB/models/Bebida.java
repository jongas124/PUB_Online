package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("bebida")
@Data
@EqualsAndHashCode(callSuper=false)
public class Bebida extends ItemMenu{
    
    @Column(name = "tamanho")
    private String tamanho;

    @Column(name = "estoque")
    private Integer quantidadeEstoque;
}
