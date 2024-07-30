package com.PUB_Online.PUB.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {
    public static final String TABLE_NAME = "clientes";

    @Id
    @Column(name = "cpf", length = 12, unique = true, updatable = false, nullable = false)
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @Column(name = "nome", nullable = false)
    @NotBlank
    private String nome;

    @ElementCollection
    @CollectionTable(name = "cliente_telefones")
    private List<String> telefones = new ArrayList<String>();

    @Column(name = "email", unique = true, nullable = false, updatable = false)
    @NotBlank
    @Size(min = 5)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Column(name = "password", length = 127, nullable = false)
    @NotBlank
    @Size(min = 8, max = 120)
    private String password;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Reserva> reservas = new ArrayList<Reserva>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<Pedido>();

}
