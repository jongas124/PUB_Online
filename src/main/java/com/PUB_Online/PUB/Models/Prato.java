package com.PUB_Online.PUB.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="pratos")
@Getter
@Setter
public class Prato {
    @Column(name = "descricao", nullable = false)
    @NotBlank
    private String descrição;

    @Column(name = "disponivel", nullable = false)
    @NotBlank
    private boolean disponivel;

    @Size(max=255)
    private Categoria categoria;
    
    public enum Categoria{}
    
}
