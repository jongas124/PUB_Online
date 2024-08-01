package com.PUB_Online.PUB.controllers.dtos;

import java.util.Set;

public record ClienteUpdateDTO(String nome, String password, Set<String> telefones) {}
