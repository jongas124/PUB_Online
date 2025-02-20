package com.PUB_Online.PUB.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {
    public static final String TABLE_NAME = "clientes";

    @Id
    @Column(name = "cpf", length = 16, unique = true, updatable = false, nullable = false)
    @NotBlank
    @Size(min = 11, max = 15)
    private String cpf;

    @Column(name = "nome", nullable = false)
    @NotBlank
    private String nome;

    @ElementCollection
    @CollectionTable(name = "cliente_telefones")
    private Set<String> telefones = new HashSet<String>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private List<Reserva> reservas = new ArrayList<Reserva>();

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @JoinColumn(name = "comanda_numero", referencedColumnName = "numero")
    private Comanda comanda;

    @Getter
    @AllArgsConstructor
    public enum Role {
        CLIENTE(1, "cliente");

        private int code;
        private String descricao;
    }

}
