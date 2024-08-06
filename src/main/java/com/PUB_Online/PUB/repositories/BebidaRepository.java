package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Bebida;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    Bebida findByNome(String nome);
    
}
