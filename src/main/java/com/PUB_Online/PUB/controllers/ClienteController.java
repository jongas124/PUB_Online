package com.PUB_Online.PUB.controllers;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.ClienteCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.ClienteUpdateDTO;
import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
@RequestMapping("/cliente")
@Validated
@EnableMethodSecurity
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;
    
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClienteCreateDTO obj) {
        Cliente cliente = this.clienteService.fromDTO(obj);
        Cliente newCliente = this.clienteService.create(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cpf}").buildAndExpand(newCliente.getCpf()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Cliente> findByCpf(@PathVariable String login, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Cliente>> findByNome(@PathVariable String nome, JwtAuthenticationToken token) {
        List<Cliente> obj = this.clienteService.findByNome(nome);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/telefone/{login}")
    public ResponseEntity<Set<String>> findTelefone(@PathVariable String login, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        return ResponseEntity.ok().body(obj.getTelefones());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> list = this.clienteService.findAllClientes();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{login}")
    public ResponseEntity<Void> update(@PathVariable String login, @RequestBody ClienteUpdateDTO dto, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        this.clienteService.update(obj, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("telefone/{login}")
    public ResponseEntity<Void> updateTelefone(@PathVariable String login, @RequestBody Set<String> telefones, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        this.clienteService.addTelefones(obj.getCpf(), telefones);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> delete(@PathVariable String login, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        this.clienteService.deleteCliente(obj.getCpf());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/telefone/{login}")
    public ResponseEntity<Void> deleteTelefone(@PathVariable String login, @RequestBody Set<String> telefones, JwtAuthenticationToken token) {
        clienteService.hasPermision(login, token);
        Cliente obj = this.clienteService.findByCpfOrEmail(login);
        this.clienteService.deleteTelefones(obj.getCpf(), telefones);
        return ResponseEntity.noContent().build();
    }
        
}