package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "itens_menu")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public abstract class ItemMenu {

    public static final String TABLE_NAME = "itens_menu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome", nullable = false)
    @NotBlank
    private String nome;

    @Column(name = "preco", nullable = false) //Considerar trocar float por double ou BigDecimal
    @NotBlank
    private float preco;

}
