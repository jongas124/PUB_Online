package com.PUB_Online.PUB.controllers.dtos;

import java.math.BigDecimal;

public record BebidaCreateUpdateDTO(String nome, BigDecimal preco, String tamanho, Integer quantidadeEstoque) {
}
