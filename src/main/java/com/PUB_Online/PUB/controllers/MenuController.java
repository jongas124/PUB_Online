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

import com.PUB_Online.PUB.controllers.dtos.BebidaCreateUpdateDTO;
import com.PUB_Online.PUB.controllers.dtos.ItemMenuReponseDTO;
import com.PUB_Online.PUB.controllers.dtos.PratoCreateUpdateDTO;
import com.PUB_Online.PUB.models.Bebida;
import com.PUB_Online.PUB.models.Prato;
import com.PUB_Online.PUB.services.MenuService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/menu")
@Validated
@EnableMethodSecurity
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuReponseDTO> findPratoById(@PathVariable Long id) {
        ItemMenuReponseDTO item = this.menuService.findById(id);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemMenuReponseDTO>> findAll() {
        List<ItemMenuReponseDTO> item = this.menuService.findAll();
        return ResponseEntity.ok().body(item);
    }
    
    @PostMapping("/prato")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> createPrato(@RequestBody PratoCreateUpdateDTO obj) {
        Prato prato = this.menuService.fromDTO(obj);
        Prato newPrato = this.menuService.createPrato(prato);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newPrato.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/bebida")
    public ResponseEntity<Void> createBebida(@RequestBody BebidaCreateUpdateDTO obj) {
        Bebida bebida = this.menuService.fromDTO(obj);
        Bebida newBebida = this.menuService.createBebida(bebida);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newBebida.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/prato/{id}")
    public ResponseEntity<Void> updatePrato(@PathVariable Long id, @RequestBody PratoCreateUpdateDTO dto) {
        Prato prato = this.menuService.findPratoById(id);
        this.menuService.updatePrato(prato, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/bebida/{id}")
    public ResponseEntity<Void> updateBebida(@PathVariable Long id, @RequestBody BebidaCreateUpdateDTO dto) {
        Bebida bebida = this.menuService.findBebidaById(id);
        this.menuService.updateBebida(bebida, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.menuService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
