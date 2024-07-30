package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Reserva findByNumero(Long numero);
    
}
