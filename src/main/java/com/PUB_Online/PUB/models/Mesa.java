package com.PUB_Online.PUB.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mesa")
@Getter @Setter
public class Mesa {
    public static final String TABLE_NAME = "mesas";
    
    @Id
    @Column (name = "numero", unique = true)
    private Long numero;

    @Column(name = "capacidade", nullable = false)
    @NotNull
    private Integer capacidade;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Reserva> reservas = new ArrayList<Reserva>();

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "comanda_numero", referencedColumnName = "numero")
    private Comanda comanda;

}
