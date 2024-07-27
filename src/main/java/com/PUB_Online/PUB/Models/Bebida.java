package com.PUB_Online.PUB.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Bebida extends ItemMenu{
    private String tamanho;
    private int estoque;

    public int getEstoque() {
        return estoque;
    }
    public String getTamanho() {
        return tamanho;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
