package com.PUB_Online.PUB.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.ReservaCreateDTO;
import com.PUB_Online.PUB.models.Reserva;
import com.PUB_Online.PUB.services.ClienteService;
import com.PUB_Online.PUB.services.ReservaService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/reserva")
@Validated
@EnableMethodSecurity
public class ReservaController {
    
    @Autowired
    ReservaService reservaService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservaCreateDTO reserva, JwtAuthenticationToken token) {
        Reserva obj = this.reservaService.fromDTO(reserva);
        Reserva newObj = this.reservaService.create(token.getName(), obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(newObj.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PostMapping("/{login}")
    public ResponseEntity<Void> createViaLogin(@PathVariable String login, @RequestBody ReservaCreateDTO reserva) {
        Reserva obj = this.reservaService.fromDTO(reserva);
        Reserva newObj = this.reservaService.create(this.clienteService.findByCpfOrEmail(login).getCpf(), obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(newObj.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> findReservasCliente(JwtAuthenticationToken token) {
        List<Reserva> obj = this.reservaService.findByCpf(token.getName());
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/{login}")
    public ResponseEntity<List<Reserva>> findReservasCliente(@PathVariable String login) {
        List<Reserva> obj = this.reservaService.findByCpf(this.clienteService.findByCpfOrEmail(login).getCpf());
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/all")
    public ResponseEntity<List<Reserva>> findAll() {
        List<Reserva> obj = this.reservaService.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("id/{numero}")
    public ResponseEntity<Reserva> findByNumero(@PathVariable Long numero) {
        Reserva obj = this.reservaService.findById(numero);
        return ResponseEntity.ok().body(obj);
    }

    @Transactional
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> delete(@PathVariable Long numero, JwtAuthenticationToken token) {
        this.reservaService.delete(numero, token.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @DeleteMapping("/admin/{numero}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long numero) {
        this.reservaService.deleteAdmin(numero);
        return ResponseEntity.noContent().build();
    }
}
