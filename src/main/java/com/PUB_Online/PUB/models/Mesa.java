package com.PUB_Online.PUB.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mesa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mesa {
    public static final String TABLE_NAME = "mesas";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "numero", unique = true)
    private int numero;

    @Column(name = "capacidade", nullable = false)
    @NotNull
    private int capacidade;

    @Column(name = "status", nullable = false)
    @NotNull
    private Status status;
    
    @Getter
    @AllArgsConstructor
    public enum Status {
        LIVRE(1, "Reservada"),
        RESERVADA(2, "Livre"),
        OCUPADA(3, "Ocupada"),;

        private int code;
        private String descricao;
    }
}
