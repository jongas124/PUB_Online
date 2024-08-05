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
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Long pedidoId, JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        Pedido pedido = this.pedidoService.findById(pedidoId, token.getName());
        Comanda comanda = this.comandaService.create(cliente, pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(comanda.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PostMapping("/mesa/{mesaId}")
    public ResponseEntity<Void> createViaMesa(@PathVariable Long mesaId, @RequestBody Long pedidoId) {
        Pedido pedido = this.pedidoService.findByIdGarcom(pedidoId);
        Comanda comanda = this.comandaService.createViaMesa(mesaId, pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{numero}").buildAndExpand(comanda.getNumero()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Comanda> findByToken(JwtAuthenticationToken token) {
        Comanda obj = this.clienteService.findByCpf(token.getName()).getComanda();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/mesa/{numeroMesa}")
    public ResponseEntity<Comanda> findByMesa(@PathVariable Long numeroMesa) {
        Comanda obj = this.comandaService.findByMesa(numeroMesa);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping
    public ResponseEntity<Comanda> update(@RequestBody Long pedidoId, JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        Pedido pedido = this.pedidoService.findById(pedidoId, token.getName());
        Comanda comanda = this.comandaService.update(cliente, pedido);
        return ResponseEntity.ok().body(comanda);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PutMapping("/mesa/{numeroMesa}")
    public ResponseEntity<Comanda> updateViaMesa(@PathVariable Long numeroMesa, @RequestBody Long pedidoId) {
        Pedido pedido = this.pedidoService.findByIdGarcom(pedidoId);
        Comanda comanda = this.comandaService.updateViaMesa(numeroMesa, pedido);
        return ResponseEntity.ok().body(comanda);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(JwtAuthenticationToken token) {
        Cliente cliente = this.clienteService.findByCpf(token.getName());
        this.comandaService.delete(cliente);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @DeleteMapping("/mesa/{numeroMesa}")
    public ResponseEntity<Void> deleteViaMesa(@PathVariable Long numeroMesa) {
        this.comandaService.deleteViaMesa(numeroMesa);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/admin/{idMesa}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long idMesa) {
        this.comandaService.deleteAdmin(idMesa);
        return ResponseEntity.noContent().build();
    }
}
