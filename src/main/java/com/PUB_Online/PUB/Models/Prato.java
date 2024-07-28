package com.PUB_Online.PUB.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pratos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Prato extends ItemMenu {

    @Column(name = "descricao", nullable = false, length = 255)
    @NotBlank
    @Size(min = 10, max = 250)
    private String descricao;

    @Column(name = "disponivel", nullable = false)
    @NotNull
    private boolean disponivel;

    @Column(name = "categoria", nullable = false)
    @NotNull
    private Categoria categoria;
    
    @Getter
    @AllArgsConstructor
    public enum Categoria {
        ENTRADA(1),
        PRATOPRINCIPAL(2),
        SOBREMESA(3),
        DRINK(4);

        private int code;
    }
    
}
