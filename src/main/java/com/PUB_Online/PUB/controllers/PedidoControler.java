package com.PUB_Online.PUB.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.ItemPedidoDTO;
import com.PUB_Online.PUB.models.Pedido;
import com.PUB_Online.PUB.models.Pedido.Status;
import com.PUB_Online.PUB.services.PedidoService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/pedido")
@Validated
@EnableMethodSecurity
public class PedidoControler {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody List<ItemPedidoDTO> itens) {
        Pedido obj = this.pedidoService.create(itens);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id, JwtAuthenticationToken token) {
        Pedido obj = this.pedidoService.findById(id, token.getName());
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @GetMapping("/garcom/{id}")
    public ResponseEntity<Pedido> findByIdGarcom(@PathVariable Long id) {
        Pedido obj = this.pedidoService.findByIdGarcom(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody Status status) {
    this.pedidoService.updateStatus(id, status);
    return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {
        this.pedidoService.delete(id, token.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GARCOM')")
    @DeleteMapping("/garcom/{id}")
    public ResponseEntity<Void> deleteGarcom(@PathVariable Long id) {
        this.pedidoService.deleteGarcom(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        this.pedidoService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
    
}
