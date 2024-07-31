package com.PUB_Online.PUB.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "garcons")
@Data
public class Garcom {
    public static final String TABLE_NAME = "garcons";

    @Id
    @Column(name = "cpf", length = 16, unique = true, nullable = false, updatable = false)
    @NotBlank
    @Size(min = 11, max = 15)
    private String cpf;

    @Column(name = "nome", nullable = false)
    @NotBlank
    private String nome;

    @ElementCollection
    @CollectionTable(name = "garcom_telefones")
    private List<String> telefones = new ArrayList<String>();

    @Column(name = "username", length = 65, unique = true, nullable = false, updatable = false)
    @NotBlank
    @Size(min = 4, max = 64)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    @Column(name = "password", length = 121, nullable = false)
    @NotBlank
    @Size(min = 8, max = 120)
    private String password;
    

}
