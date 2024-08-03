package com.PUB_Online.PUB.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.exceptions.DuplicatedValueException;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.ItemMenu;
import com.PUB_Online.PUB.models.ItemPedido;
import com.PUB_Online.PUB.models.ItemPedidoId;
import com.PUB_Online.PUB.models.Pedido;
import com.PUB_Online.PUB.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public ItemPedido ExibeItemMenu(ItemMenu itemMenu, Pedido pedido) {
        ItemPedidoId id = new ItemPedidoId();
        id.setItemMenuId(itemMenu.getId());
        id.setPedidoId(pedido.getId());
        Optional<ItemPedido> itemPedido = itemPedidoRepository.findById(id);
        if(itemPedido.isPresent()) {
            return itemPedido.get();
        } else {
            throw new ObjectNotFoundException("Item não encontrado");
        }
    }

    public ItemPedido novoItemPedido(ItemMenu itemMenu, Pedido pedido, int quantidade) {
        ItemPedido obj = new ItemPedido();
        try {
            this.ExibeItemMenu(itemMenu, pedido);
            throw new DuplicatedValueException("Item já adicionado ao pedido");
        } catch (ObjectNotFoundException e) {
        }
        obj.setItemMenu(itemMenu);
        obj.setPedido(pedido);
        if (quantidade <= 0) {
            throw new InvalidNumberException("Quantidade inválida");
        }
        obj.setQuantidade(quantidade);
        return this.itemPedidoRepository.save(obj);
    }

    public ItemPedido atualizaQuantidade(ItemMenu itemMenu, Pedido pedido, int quantidade) {
        ItemPedido obj = this.ExibeItemMenu(itemMenu, pedido);
        if (quantidade <= 0) {
            throw new InvalidNumberException("Quantidade inválida");
        }
        obj.setQuantidade(quantidade);
        return this.itemPedidoRepository.save(obj);
    }

    public void deletaItemPedido(ItemMenu itemMenu, Pedido pedido) {
        ItemPedido obj = this.ExibeItemMenu(itemMenu, pedido);
        this.itemPedidoRepository.delete(obj);
    }
}
