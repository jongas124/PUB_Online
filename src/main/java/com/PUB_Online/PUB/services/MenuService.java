package com.PUB_Online.PUB.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.controllers.dtos.BebidaCreateUpdateDTO;
import com.PUB_Online.PUB.controllers.dtos.ItemMenuReponseDTO;
import com.PUB_Online.PUB.controllers.dtos.PratoCreateUpdateDTO;
import com.PUB_Online.PUB.exceptions.InvalidNumberException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.models.Bebida;
import com.PUB_Online.PUB.models.Prato;
import com.PUB_Online.PUB.repositories.BebidaRepository;
import com.PUB_Online.PUB.repositories.PratoRepository;

@Service
public class MenuService {
    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private PratoRepository pratoRepository;

    public Bebida createBebida(Bebida obj) {
        obj.setId(null);
        if (obj.getPreco().signum() == -1 || obj.getPreco().signum() == 0) {
            throw new InvalidNumberException("Preço inválido");
        }
        if (obj.getQuantidadeEstoque() == null || obj.getQuantidadeEstoque() < 0) {
            throw new InvalidNumberException("Quantidade inválida");
        }
        if(obj.getTamanho() == null) {
            throw new InvalidNumberException("Tamanho inválido");
        }

        return this.bebidaRepository.save(obj);
    }

    public Prato createPrato(Prato obj) {
        obj.setId(null);
        if (obj.getPreco().signum() == -1 || obj.getPreco().signum() == 0) {
            throw new InvalidNumberException("Preço inválido");
        }
        if(obj.getCategoria() == null) {
            throw new InvalidNumberException("Categoria inválida");
        }
        if (obj.getDisponivel() == null) {
            throw new InvalidNumberException("Disponibilidade inválida");
        }
        
        return this.pratoRepository.save(obj);
    }

    public Prato findPratoById(Long id) {
        Optional<Prato> prato = this.pratoRepository.findById(id);
        if(prato.isPresent()) {
            return prato.get();
        } else {
            throw new ObjectNotFoundException("Prato do menu não encontrado");
        }
    }

    public Bebida findBebidaById(Long id) {
        Optional<Bebida> bebida = this.bebidaRepository.findById(id);
        if(bebida.isPresent()) {
            return bebida.get();
        } else {
            throw new ObjectNotFoundException("Bebida do menu não encontrado");
        }
    }

    public ItemMenuReponseDTO findById(Long id) {
        try {
            return this.toDTO(this.findPratoById(id));
        } catch (ObjectNotFoundException e) {
            try {
                return this.toDTO(this.findBebidaById(id));
            } catch (ObjectNotFoundException ex) {
                throw new ObjectNotFoundException("Item do menu não encontrado");
            }
        }
    }

    public List<ItemMenuReponseDTO> findAllAdmin() {
        List<ItemMenuReponseDTO> itens = this.pratoRepository.findAll().stream().map(prato -> this.toDTO(prato)).collect(Collectors.toList());
        itens.addAll(this.bebidaRepository.findAll().stream().map(bebida -> this.toDTO(bebida)).collect(Collectors.toList()));
        return itens;
    }

    public List<ItemMenuReponseDTO> findAll() {
        List<ItemMenuReponseDTO> itens = this.pratoRepository.findAll().stream().map(prato -> this.toDTO(prato)).collect(Collectors.toList());
        itens.addAll(this.bebidaRepository.findAll().stream().map(bebida -> this.toDTO(bebida)).collect(Collectors.toList()));
        return itens.stream().filter(item -> item.disponivel() == true).collect(Collectors.toList());
    }

    public void updatePrato(Prato obj, PratoCreateUpdateDTO update) {
        Prato newObj = this.findPratoById(obj.getId());
        if (update.nome() != null) {
            newObj.setNome(update.nome());
        }
        if(update.preco() != null) {
            newObj.setPreco(update.preco());
        }
        if(update.descricao() != null) {
            newObj.setDescricao(update.descricao());
        }
        if(update.categoria() != null) {
            newObj.setCategoria(update.categoria());
        }
        if (update.disponivel() != obj.getDisponivel()) {
            newObj.setDisponivel(update.disponivel());
        }
        this.pratoRepository.save(newObj);
    }

    public void updateBebida(Bebida obj, BebidaCreateUpdateDTO update) {
        Bebida newObj = this.findBebidaById(obj.getId());
        if (update.nome() != null) {
            newObj.setNome(update.nome());
        }
        if(update.preco() != null) {
            newObj.setPreco(update.preco());
        }
        if(update.tamanho() != null) {
            newObj.setTamanho(update.tamanho());
        }
        if(update.quantidadeEstoque() != null) {
            newObj.setQuantidadeEstoque(update.quantidadeEstoque());
        }
        this.bebidaRepository.save(newObj);
    }

    public void delete(Long id) {
        this.pratoRepository.deleteById(id);
        this.bebidaRepository.deleteById(id);
    }

    public Prato fromDTO(PratoCreateUpdateDTO obj) {
        Prato newobj = new Prato();
        newobj.setNome(obj.nome());
        newobj.setPreco(obj.preco());
        newobj.setDescricao(obj.descricao());
        newobj.setDisponivel(obj.disponivel());
        newobj.setCategoria(obj.categoria());
        return newobj;
    }

    public Bebida fromDTO(BebidaCreateUpdateDTO obj) {
        Bebida newobj = new Bebida();
        newobj.setNome(obj.nome());
        newobj.setPreco(obj.preco());
        newobj.setTamanho(obj.tamanho());
        newobj.setQuantidadeEstoque(obj.quantidadeEstoque());
        return newobj;
    }

    public ItemMenuReponseDTO toDTO(Prato prato) {
        ItemMenuReponseDTO newDto;
        newDto = new ItemMenuReponseDTO(prato.getId(), prato.getNome(), "Prato", prato.getPreco(), prato.getDescricao(), 
                                            prato.getDisponivel(), prato.getCategoria(), null, null);
        return newDto;
    }

    public ItemMenuReponseDTO toDTO(Bebida bebida) {
        ItemMenuReponseDTO newDto;
        newDto = new ItemMenuReponseDTO(bebida.getId(), bebida.getNome(), "Bebida", bebida.getPreco(), null, 
                                            null, null, bebida.getTamanho(), bebida.getQuantidadeEstoque());
        return newDto;
    }



}
