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

import com.PUB_Online.PUB.controllers.dtos.GarcomCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.GarcomUpdateDTO;
import com.PUB_Online.PUB.models.Garcom;
import com.PUB_Online.PUB.services.GarcomService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
@RestController
@RequestMapping("/garcom")
@Validated
@EnableMethodSecurity
public class GarcomController {
    
    @Autowired
    GarcomService garcomService;
    
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GarcomCreateDTO obj) {
        Garcom garcom = this.garcomService.fromDTO(obj);
        Garcom newGarcom = this.garcomService.create(garcom);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cpf}").buildAndExpand(newGarcom.getCpf()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Garcom> findByCpf(@PathVariable String login, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Garcom>> findByNome(@PathVariable String nome, JwtAuthenticationToken token) {
        List<Garcom> obj = this.garcomService.findByNome(nome);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/telefone/{login}")
    public ResponseEntity<Set<String>> findTelefone(@PathVariable String login, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        return ResponseEntity.ok().body(obj.getTelefones());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Garcom>> findAll(JwtAuthenticationToken token) {
        List<Garcom> list = this.garcomService.findAllGarcoms();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{login}")
    public ResponseEntity<Void> update(@PathVariable String login, @RequestBody GarcomUpdateDTO newGarcom, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        this.garcomService.update(obj, newGarcom);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("telefone/{login}")
    public ResponseEntity<Void> updateTelefone(@PathVariable String login, @RequestBody Set<String> telefones, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        this.garcomService.addTelefones(obj.getCpf(), telefones);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> delete(@PathVariable String login, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        this.garcomService.deleteGarcom(obj.getCpf());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/telefone/{login}")
    public ResponseEntity<Void> deleteTelefone(@PathVariable String login, @RequestBody Set<String> telefones, JwtAuthenticationToken token) {
        garcomService.hasPermision(login, token);
        Garcom obj = this.garcomService.findByCpfOrUsername(login);
        this.garcomService.deleteTelefones(obj.getCpf(), telefones);
        return ResponseEntity.noContent().build();
    }
        
}
