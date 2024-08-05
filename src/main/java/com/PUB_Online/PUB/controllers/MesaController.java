package com.PUB_Online.PUB.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.MesaCreateDTO;
import com.PUB_Online.PUB.models.Mesa;
import com.PUB_Online.PUB.services.MesaService;

@RestController
@RequestMapping("/mesa")
@Validated
@EnableMethodSecurity
public class MesaController {
    @Autowired
    MesaService mesaService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MesaCreateDTO mesa) {
        Mesa obj = this.mesaService.fromDTO(mesa);
        Mesa newObj = this.mesaService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(newObj.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/all")
    public ResponseEntity<List<Mesa>> findAll() {
        List<Mesa> obj = this.mesaService.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/{numero}")
    public ResponseEntity<Mesa> findByNumero(@PathVariable Long numero) {
        Mesa obj = this.mesaService.findById(numero);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/livres")
    public ResponseEntity<List<Mesa>> findLivres() {
        List<Mesa> obj = this.mesaService.findLivres();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/ocupadas")
    public ResponseEntity<List<Mesa>> findOcupadas() {
        List<Mesa> obj = this.mesaService.findOcupadas();
        return ResponseEntity.ok().body(obj);
    }
    
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{numero}")
    public ResponseEntity<Void> update(@PathVariable Long numero, @RequestBody Integer novaCapacidade) {
        this.mesaService.update(numero, novaCapacidade);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> delete(@PathVariable Long numero) {
        this.mesaService.delete(numero);
        return ResponseEntity.noContent().build();
    }
}
