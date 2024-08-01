package com.PUB_Online.PUB.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PUB_Online.PUB.controllers.dtos.ClienteCreateDTO;
import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/cliente")
@Validated
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> findByCpf(@RequestParam String cpf) {
        Cliente obj = this.clienteService.findByCpf(cpf);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClienteCreateDTO obj) {
        Cliente cliente = this.clienteService.fromDTO(obj);
        Cliente newCliente = this.clienteService.create(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cpf}").buildAndExpand(newCliente.getCpf()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    
}
