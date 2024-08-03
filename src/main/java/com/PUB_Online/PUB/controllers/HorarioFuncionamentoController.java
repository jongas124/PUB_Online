package com.PUB_Online.PUB.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.HorarioFuncionamentoCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.HorarioFuncionamentoUpdateDTO;
import com.PUB_Online.PUB.models.HorarioFuncionamento;
import com.PUB_Online.PUB.models.HorarioFuncionamento.DiasSemana;
import com.PUB_Online.PUB.services.HorarioFuncionamentoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/horario")
@Validated
@EnableMethodSecurity
public class HorarioFuncionamentoController {
    
    @Autowired
    HorarioFuncionamentoService horarioFuncionamentoService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HorarioFuncionamentoCreateDTO horario) {
        HorarioFuncionamento horarioFuncionamento = this.horarioFuncionamentoService.fromDTO(horario);
        HorarioFuncionamento newHorario = this.horarioFuncionamentoService.create(horarioFuncionamento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{diassemana}").buildAndExpand(newHorario.getDiasSemana()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{dia}")
    public ResponseEntity<HorarioFuncionamento> findByDiaSemana(@PathVariable DiasSemana dia) {
        HorarioFuncionamento obj = this.horarioFuncionamentoService.findByDiaSemana(dia);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<HorarioFuncionamento>> findAll() {
        List<HorarioFuncionamento> obj = this.horarioFuncionamentoService.findAllDias();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{dia}")
    public ResponseEntity<HorarioFuncionamento> update(@PathVariable DiasSemana dia, @RequestBody HorarioFuncionamentoUpdateDTO newHorario) {
        HorarioFuncionamento obj = this.horarioFuncionamentoService.findByDiaSemana(dia);
        this.horarioFuncionamentoService.update(obj, newHorario);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{dia}")
    public ResponseEntity<Void> delete(@PathVariable DiasSemana dia) {
        HorarioFuncionamento horarioFuncionamento = this.horarioFuncionamentoService.findByDiaSemana(dia);
        this.horarioFuncionamentoService.delete(horarioFuncionamento.getDiasSemana());
        return ResponseEntity.noContent().build();
    }   

}
