package com.PUB_Online.PUB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PUB_Online.PUB.models.HorarioFuncionamento;
import com.PUB_Online.PUB.models.HorarioFuncionamento.DiasSemana;

@Repository
public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, DiasSemana> {    }
