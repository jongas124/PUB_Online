package com.PUB_Online.PUB.services;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.ItemPedidoDTO;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.MenuException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.Bebida;
import com.PUB_Online.PUB.models.ItemPedido;
import com.PUB_Online.PUB.models.Prato;
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
            Prato prato = this.menuService.findPratoById(item.id());
            obj.setPrato(prato);
            obj.setPrecoItemPedido(prato.getPreco());
            if (obj.getPrato().getDisponivel() == false) {
                throw new MenuException("Prato indisponível, id do prato: " + item.id());
            }
        } catch (ObjectNotFoundException e) {
            try {
                Bebida bebida = this.menuService.findBebidaById(item.id());
                obj.setBebida(bebida);
                obj.setPrecoItemPedido(bebida.getPreco());
                if ((bebida.getQuantidadeEstoque() - item.quantidade()) <= 0) {
                    throw new MenuException("Quantidade da Bebida selecionada indisponível, id da bebida: " + item.id());
                } else {
                    bebida.setQuantidadeEstoque(bebida.getQuantidadeEstoque() - item.quantidade());
                    this.menuService.updateBebidaQuantidade(bebida);
                }
            } catch (ObjectNotFoundException e2) {
                throw new ObjectNotFoundException("Item não encontrado, id do item" + item.id());
            }
        }
        if (item.quantidade() <= 0) {
            throw new InvalidNumberException("Quantidade inválida");
        }
        obj.setQuantidade(item.quantidade());
        obj.getPrecoItemPedido().multiply(new BigDecimal(item.quantidade()));
        return this.itemPedidoRepository.save(obj);
    }

}
