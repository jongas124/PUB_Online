package com.PUB_Online.PUB.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Garcom;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, String> {
    Garcom findByNome(String nome);
    Optional<Garcom> findByCpf(String cpf);
    List<Garcom> findByNomeContaining(String nome);
    Optional<Garcom> findByUsername(String username);

    
}
