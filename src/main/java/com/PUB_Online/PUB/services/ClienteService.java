package com.PUB_Online.PUB.services;

import java.util.Optional;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.repositories.ClienteRepository;
import com.PUB_Online.PUB.exceptions.InvalidEmailException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.util.CPFUtil;


@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente findByCpf(String cpf) {
        CPFUtil.validarCPF(cpf);
        Optional<Cliente> cliente = this.clienteRepository.findByCpf(cpf);
        if(cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new ObjectNotFoundException("Usuário com o cpf: " + cpf + "não encontrado");
        }
    }

    public Cliente findByEmail(String email) {
        try {
            InternetAddress emailAddrress = new InternetAddress(email);
            emailAddrress.validate();
        } catch (Exception e) {
            throw new InvalidEmailException("Email inválido");
        }

        Optional<Cliente> cliente = this.clienteRepository.findByEmail(email);
        if(cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new ObjectNotFoundException("Usuário com o email: " + email + "não encontrado");
        }
    }


}
