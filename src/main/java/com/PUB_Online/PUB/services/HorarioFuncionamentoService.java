package com.PUB_Online.PUB.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.HorarioFuncionamentoCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.HorarioFuncionamentoUpdateDTO;
import com.PUB_Online.PUB.exceptions.DuplicatedValueException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.HorarioFuncionamento;
import com.PUB_Online.PUB.models.HorarioFuncionamento.DiasSemana;
import com.PUB_Online.PUB.repositories.HorarioFuncionamentoRepository;
@Service
public class HorarioFuncionamentoService {

    @Autowired
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    public HorarioFuncionamento findByDiaSemana(DiasSemana diaSemana) {
        Optional<HorarioFuncionamento> obj = this.horarioFuncionamentoRepository.findById(diaSemana);
        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new ObjectNotFoundException("Horário de funcionamento não encontrado");
        }
    }

    public List<HorarioFuncionamento> findAllDias() {
        return this.horarioFuncionamentoRepository.findAll();
    }

    public HorarioFuncionamento create(HorarioFuncionamento obj) {
        try {
            this.findByDiaSemana(obj.getDiasSemana());
            throw new DuplicatedValueException("Horário de funcionamento já cadastrado");
        } catch (ObjectNotFoundException e) {
            obj.setHoraAbertura(obj.getHoraAbertura().truncatedTo(ChronoUnit.MINUTES));
            obj.setHoraFechamento(obj.getHoraFechamento().truncatedTo(ChronoUnit.MINUTES));
            return this.horarioFuncionamentoRepository.save(obj);
        }
    }

    public HorarioFuncionamento update(HorarioFuncionamento obj, HorarioFuncionamentoUpdateDTO update) {
        if (update.horaAbertura() != null) {
            obj.setHoraAbertura(update.horaAbertura());
        }
        if(update.horaFechamento() != null) {
            obj.setHoraFechamento(update.horaFechamento());
        }
        return this.horarioFuncionamentoRepository.save(obj);
    }

    public void delete(DiasSemana diaSemana) {
        HorarioFuncionamento obj = this.findByDiaSemana(diaSemana);
        this.horarioFuncionamentoRepository.delete(obj);
    }

    public boolean isHorarioFuncionamento() {
        LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();
        int codigoDia = date.getDayOfWeek().getValue();
        DiasSemana diaSemana = DiasSemana.fromCode(codigoDia);
        HorarioFuncionamento obj = this.findByDiaSemana(diaSemana);
        if(!now.isBefore(obj.getHoraAbertura()) && !now.isAfter(obj.getHoraFechamento())) {
            return true;
        } else {
            return false;
        }
    }

    public HorarioFuncionamento fromDTO(HorarioFuncionamentoCreateDTO dto) {
        HorarioFuncionamento obj = new HorarioFuncionamento();
        obj.setDiasSemana(dto.diasSemana());
        obj.setHoraAbertura(dto.horaAbertura());
        obj.setHoraFechamento(dto.horaFechamento());
        return obj;
    }


}
