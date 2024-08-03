package com.PUB_Online.PUB.controllers.dtos;

import java.math.BigDecimal;

import com.PUB_Online.PUB.models.Prato.Categoria;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemMenuReponseDTO(Long id, String nome, String type, BigDecimal preco, String descricao, Boolean disponivel,
                                    Categoria categoria, String tamanho, Integer quantidadeEstoque) {}
