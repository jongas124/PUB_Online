package com.PUB_Online.PUB.controllers;

import java.net.URI;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.models.Comanda;
import com.PUB_Online.PUB.models.Pedido;
import com.PUB_Online.PUB.services.ClienteService;
import com.PUB_Online.PUB.services.ComandaService;
import com.PUB_Online.PUB.services.PedidoService;


@RestController
@RequestMapping("/comanda")
@Validated
@EnableMethodSecurity
public class ComandaController {
    
    @Autowired
    private ComandaService comandaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{pedidoId}")
    public ResponseEntity<Void> create(@PathVariable Long pedidoId, JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        Pedido pedido = this.pedidoService.findByIdCreateUpdate(pedidoId, token.getName());
        Comanda comanda = this.comandaService.create(cliente, pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(comanda.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Comanda> findByToken(JwtAuthenticationToken token) {
        Comanda obj = this.clienteService.findByCpf(token.getName()).getComanda();
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<Void> update(@PathVariable Long pedidoId, JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        Pedido pedido = this.pedidoService.findByIdCreateUpdate(pedidoId, token.getName());
        this.comandaService.update(cliente, pedido);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        this.comandaService.delete(cliente);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PostMapping("/mesa/{mesaId}/pedido/{pedidoId}")
    public ResponseEntity<Void> createViaMesa(@PathVariable("mesaId") Long mesaId, @PathVariable("pedidoId") Long pedidoId) {
        Pedido pedido = this.pedidoService.findByIdGarcom(pedidoId);
        Comanda comanda = this.comandaService.createViaMesa(mesaId, pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(comanda.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/mesa/{numeroMesa}")
    public ResponseEntity<Comanda> findByMesa(@PathVariable Long numeroMesa) {
        Comanda obj = this.comandaService.findByMesa(numeroMesa);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PutMapping("/mesa/{numeroMesa}/pedido/{pedidoId}")
    public ResponseEntity<Void> updateViaMesa(@PathVariable("numeroMesa") Long numeroMesa, @PathVariable("pedidoId") Long pedidoId) {
        Pedido pedido = this.pedidoService.findByIdGarcom(pedidoId);
        this.comandaService.updateViaMesa(numeroMesa, pedido);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @DeleteMapping("/mesa/{numeroMesa}")
    public ResponseEntity<Void> deleteViaMesa(@PathVariable Long numeroMesa) {
        this.comandaService.deleteViaMesa(numeroMesa);
        return ResponseEntity.noContent().build();
    }
}
