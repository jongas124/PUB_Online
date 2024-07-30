package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Mesa findByNumero(Long numero);
    
}
