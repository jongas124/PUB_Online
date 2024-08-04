package com.PUB_Online.PUB.services;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.ItemPedidoDTO;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.MenuException;
import com.PUB_Online.PUB.models.ItemPedido;
import com.PUB_Online.PUB.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private MenuService menuService;

    public ItemPedido fromDTO(ItemPedidoDTO item) {
        ItemPedido obj = new ItemPedido();
        obj.setId(null);
        try {
            obj.setPrato(this.menuService.findPratoById(item.id()));
            if (obj.getPrato().getDisponivel() == false) {
                throw new MenuException("Prato indisponível");
            }
        } catch (Exception e) {
            obj.setBebida(this.menuService.findBebidaById(item.id()));
            if (obj.getBebida().getQuantidadeEstoque() <= 0) {
                throw new MenuException("Bebida indisponível");
            }
        }
        if (item.quantidade() <= 0) {
            throw new InvalidNumberException("Quantidade inválida");
        }
        obj.setQuantidade(item.quantidade());
        obj.setPrecoItemPedido(new BigDecimal("0.00"));
        obj.getPrecoItemPedido().multiply(new BigDecimal(item.quantidade()));
        return this.itemPedidoRepository.save(obj);
    }

}
