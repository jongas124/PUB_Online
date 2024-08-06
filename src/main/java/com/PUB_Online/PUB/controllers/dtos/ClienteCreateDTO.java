package com.PUB_Online.PUB.controllers.dtos;

import java.util.Set;

public record ClienteCreateDTO(String cpf, String nome, String email, String password, Set<String> telefones) {} 
