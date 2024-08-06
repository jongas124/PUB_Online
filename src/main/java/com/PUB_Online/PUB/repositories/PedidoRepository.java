package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {}
