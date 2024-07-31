package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Garcom;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, String> {
    Garcom findByNome(String nome);
    Garcom findByCpf(String cpf);
    
}
