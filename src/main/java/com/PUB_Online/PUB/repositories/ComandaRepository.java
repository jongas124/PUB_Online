package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Comanda;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    Comanda findByMesaId(Long mesaId);
    Comanda findByNumero(Long numero);
    
}
