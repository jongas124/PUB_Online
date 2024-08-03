package com.PUB_Online.PUB.controllers.dtos;

import java.math.BigDecimal;

import com.PUB_Online.PUB.models.Prato.Categoria;

public record PratoCreateUpdateDTO(String nome, BigDecimal preco, String descricao, Boolean disponivel, Categoria categoria) {}
