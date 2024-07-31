package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.Prato;
import com.PUB_Online.PUB.models.Prato.Categoria;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long> {
    Prato findByNome(String nome);
    Prato findByCategoria(Categoria categoria);
}
