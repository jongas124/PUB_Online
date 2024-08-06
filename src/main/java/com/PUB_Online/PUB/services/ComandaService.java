package com.PUB_Online.PUB.services;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.exceptions.ComandaException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.models.Comanda;
import com.PUB_Online.PUB.models.Mesa;
import com.PUB_Online.PUB.models.Pedido;
import com.PUB_Online.PUB.repositories.ComandaRepository;

@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    public Comanda create(Cliente cliente, Pedido pedido) {
        if (cliente.getComanda() != null) {
            throw new ComandaException("Este cliente já possui comanda");
        }
        Comanda comanda = new Comanda();
        comanda.setPedidos(List.of(pedido));
        comanda.setValorTotal(new BigDecimal("0.00").add(pedido.getPreco()));
        comanda.setCliente(cliente);
        comanda = this.comandaRepository.save(comanda);
        pedido.setComanda(comanda);
        this.pedidoService.savePedido(pedido);
        cliente.setComanda(comanda);
        this.clienteService.saveCliente(cliente);
        return comanda;
    }

    public Comanda findByCliente(Cliente cliente) {
        Optional<Comanda> comanda = this.comandaRepository.findById(cliente.getComanda().getNumero());
        if (comanda.isPresent()) {
            return comanda.get();
        } else {
            throw new ObjectNotFoundException("Este cliente não possui comanda");
        }
    }

    public Comanda update(Cliente cliente, Pedido pedido) {
        if (cliente.getComanda() == null) {
            return this.create(cliente, pedido);
        }
        Comanda comanda = cliente.getComanda();
        comanda.getPedidos().add(pedido);
        comanda.setValorTotal(comanda.getValorTotal().add(pedido.getPreco()));
        comanda = this.comandaRepository.save(comanda);
        pedido.setComanda(comanda);
        this.pedidoService.savePedido(pedido);
        return comanda;
    }

    public void delete(Cliente cliente) {
        Comanda comanda = cliente.getComanda();
        cliente.setComanda(null);
        comanda.setCliente(null);
        comanda.setHoraFechamentoComanda(LocalTime.now());
        this.clienteService.saveCliente(cliente);
        this.comandaRepository.save(comanda);
    }

    public Comanda createViaMesa(Long numeroMesa, Pedido pedido) {
        Comanda comanda = new Comanda();
        Mesa mesa = this.mesaService.findById(numeroMesa);
        comanda.setPedidos(List.of(pedido));
        comanda.setMesa(mesa);
        comanda.setValorTotal(new BigDecimal("0.00").add(pedido.getPreco()));
        comanda = this.comandaRepository.save(comanda);
        mesa.setComanda(comanda);
        this.mesaService.saveMesa(mesa);
        pedido.setComanda(comanda);
        this.pedidoService.savePedido(pedido);
        return comanda;
    }

    public Comanda findByMesa(Long numeroMesa) {
        Optional<Comanda> comanda = this.comandaRepository.findById(mesaService.findById(numeroMesa).getNumero());
        if (comanda.isPresent()) {
            return comanda.get();
        } else {
            throw new ObjectNotFoundException("Esta mesa não possui comanda");
        }
    }

    public Comanda updateViaMesa(Long numeroMesa, Pedido pedido) {
        Mesa mesa = this.mesaService.findById(numeroMesa);
        if (mesaService.findById(numeroMesa) == null) {
            return this.createViaMesa(numeroMesa, pedido);
        }
        Comanda comanda = mesaService.findById(numeroMesa).getComanda();
        comanda.getPedidos().add(pedido);
        comanda.setValorTotal(comanda.getValorTotal().add(pedido.getPreco()));
        comanda = this.comandaRepository.save(comanda);
        mesa.setComanda(comanda);
        this.mesaService.saveMesa(mesa);
        pedido.setComanda(comanda);
        this.pedidoService.savePedido(pedido);
        return comanda;
    }

    public void deleteViaMesa(Long numeroMesa) {
        Comanda comanda = mesaService.findById(numeroMesa).getComanda();
        Mesa mesa = mesaService.findById(numeroMesa);
        mesa.setComanda(null);
        this.mesaService.saveMesa(mesa);
        comanda.setMesa(null);
        comanda.setHoraFechamentoComanda(LocalTime.now());
        this.comandaRepository.save(comanda);
    }

    public void deleteAdmin(Long numeroMesa) {
        Comanda comanda = mesaService.findById(numeroMesa).getComanda();
        this.comandaRepository.deleteById(comanda.getNumero());
    }
}
