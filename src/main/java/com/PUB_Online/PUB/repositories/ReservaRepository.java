package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Reserva;

import jakarta.transaction.Transactional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Reserva findByNumero(Long numero);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reservas WHERE numero = :numero", nativeQuery = true)
    void deleteByNumero(@Param("numero") Long numero);
    
}
