package com.PUB_Online.PUB.controllers.dtos;

import java.util.Set;

public record GarcomCreateDTO(String cpf, String nome, String username, String password, Set<String> telefones) {} 
