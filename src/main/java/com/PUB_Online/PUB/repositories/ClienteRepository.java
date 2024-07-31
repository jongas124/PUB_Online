package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Cliente findByEmail(String email);
    Cliente findByCpf(String cpf);
    
}
