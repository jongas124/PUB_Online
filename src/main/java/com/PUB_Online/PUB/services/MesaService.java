package com.PUB_Online.PUB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.MesaCreateDTO;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.Mesa;
import com.PUB_Online.PUB.repositories.MesaRepository;

@Service
public class MesaService {
    
    @Autowired
    private MesaRepository mesaRepository;

    public Mesa create(Mesa mesa) {
        if (mesa.getNumero() == null || mesa.getNumero() <= 0) {
            throw new IllegalArgumentException("Número da mesa inválido");
        }
        if(mesa.getCapacidade() == null || mesa.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Capacidade da mesa inválida");
        }
        return this.mesaRepository.save(mesa);
    }

    public Mesa findById(Long id) {
        Optional<Mesa> obj = this.mesaRepository.findById(id);
        if(obj.isPresent()) {
            return obj.get();
        } else {
            throw new ObjectNotFoundException("Mesa não encontrada");
        }
    }

    public List<Mesa> findAll() {
        return this.mesaRepository.findAll();
    }

    public Mesa update(Long numero, Integer novaCapacidade) {
        if (numero == null || numero <= 0) {
            throw new IllegalArgumentException("Número da mesa inválido");
        }
        if(novaCapacidade == null || novaCapacidade <= 0) {
            throw new IllegalArgumentException("Capacidade da mesa inválida");
        }
        Mesa mesa = new Mesa();
        mesa.setCapacidade(novaCapacidade);
        mesa.setNumero(numero);
        return this.mesaRepository.save(mesa);
    }

    public void delete(Long id) {
        this.mesaRepository.deleteById(id);
    }

    public Mesa fromDTO(MesaCreateDTO mesa) {
        Mesa obj = new Mesa();
        obj.setNumero(mesa.numero());
        obj.setCapacidade(mesa.capacidade());
        return obj;
    }

    public Mesa saveMesa(Mesa mesa) {
        return this.mesaRepository.save(mesa);
    }
}
