package com.PUB_Online.PUB.services;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.InternetAddress;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.repositories.ClienteRepository;
import com.PUB_Online.PUB.controllers.dtos.ClienteCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.ClienteUpdateDTO;
import com.PUB_Online.PUB.exceptions.InvalidCPFException;
import com.PUB_Online.PUB.exceptions.InvalidEmailException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.util.Argon2Encoder;
import com.PUB_Online.PUB.util.CPFUtil;
import com.PUB_Online.PUB.util.EmailValidator;
import com.PUB_Online.PUB.util.PasswordValidator;
import com.PUB_Online.PUB.util.TelefoneUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private Argon2Encoder argon2Encoder;

    @Transactional
    public Cliente create(Cliente obj) {
        if(this.clienteRepository.findByCpf(CPFUtil.formatarCPF(obj.getCpf())).isPresent()) {
            throw new InvalidCPFException("CPF em uso");
        }

        //validações
        CPFUtil.validarCPF(obj.getCpf());
        EmailValidator.validarEmail(obj.getEmail());
        PasswordValidator.validarSenha(obj.getPassword());
        TelefoneUtil.validarTelefones(obj.getTelefones(), "BR");

        //formatações
        obj.setCpf(CPFUtil.formatarCPF(obj.getCpf())); 
        obj.setTelefones(TelefoneUtil.formatarTelefones(obj.getTelefones(), "BR"));
        obj.setPassword(argon2Encoder.encode(obj.getPassword()));

        return this.clienteRepository.save(obj);
    }

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

    public List<Cliente> findByNome(String nome) {
        List<Cliente> cliente = this.clienteRepository.findByNomeContaining(nome);
        if(cliente.size() > 0) {
            return cliente;
        } else {
            throw new ObjectNotFoundException("Cliente não encontrado");
        }

    }

    public List<Cliente> findAllClientes() {
        List<Cliente> users = clienteRepository.findAll();
        return users;
    }

    public Set<String> findTelefones(String cpf) {
        Cliente cliente = this.findByCpf(CPFUtil.formatarCPF(cpf));
        return cliente.getTelefones();
    }

    @Transactional
    public Cliente update(Cliente obj) {
        Cliente newobj = this.findByCpf(obj.getCpf());
        newobj.setNome(obj.getNome());
        newobj.setTelefones(TelefoneUtil.formatarTelefones(obj.getTelefones(), "BR"));
        newobj.setPassword(argon2Encoder.encode(obj.getPassword()));
        return this.clienteRepository.save(newobj);
    }

    public void deleteUser(String cpf) {
        Cliente cliente = this.findByCpf(CPFUtil.formatarCPF(cpf));
        this.clienteRepository.delete(cliente);
    }

    public void deleteTelefones(String cpf, List<String> telefones) {
        Cliente cliente = this.findByCpf(CPFUtil.formatarCPF(cpf));
        cliente.getTelefones().removeAll(telefones);
        this.clienteRepository.save(cliente);
    }

    public Cliente fromDTO(@Valid ClienteCreateDTO obj) {
        Cliente cliente = new Cliente();
        cliente.setTelefones(obj.telefones());
        cliente.setCpf(obj.cpf());
        cliente.setEmail(obj.email());
        cliente.setNome(obj.nome());
        cliente.setPassword(obj.password());
        return cliente;
    }
    
    public Cliente fromDTO(@Valid ClienteUpdateDTO obj) {
        Cliente cliente = new Cliente();
        cliente.setNome(obj.nome());
        cliente.setPassword(obj.password());
        cliente.setTelefones(obj.telefones());
        return cliente;
    }

}
