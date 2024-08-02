package com.PUB_Online.PUB.services;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.InternetAddress;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.repositories.ClienteRepository;
import com.PUB_Online.PUB.controllers.dtos.ClienteCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.ClienteUpdateDTO;
import com.PUB_Online.PUB.controllers.dtos.LoginRequestDTO;
import com.PUB_Online.PUB.exceptions.InvalidCPFException;
import com.PUB_Online.PUB.exceptions.InvalidCredentialsException;
import com.PUB_Online.PUB.exceptions.InvalidEmailException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.exceptions.PermissionException;
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

        //encode de senha
        obj.setPassword(argon2Encoder.encode(obj.getPassword()));

        return this.clienteRepository.save(obj);
    }

    public Cliente findByCpf(String cpf) {
        CPFUtil.validarCPF(cpf);
        cpf = CPFUtil.formatarCPF(cpf);
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

    public Cliente findByCpfOrEmail(String login) {
        try {
            return this.findByCpf(login);
        } catch (Exception e) {
            try {
                return this.findByEmail(login);
            } catch (Exception ex) {
                throw new ObjectNotFoundException("Login inválido");
            }
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
    public Cliente update(Cliente obj, ClienteUpdateDTO update) {
        Cliente newobj = this.findByCpf(obj.getCpf());
        if (update.nome() != null){
            newobj.setNome(update.nome());
        }
        if (update.password() != null){
            PasswordValidator.validarSenha(update.password());
            newobj.setPassword(argon2Encoder.encode(update.password()));
        }
        if (update.telefones() != null){
            TelefoneUtil.validarTelefones(update.telefones(), "BR");
            newobj.setTelefones(TelefoneUtil.formatarTelefones(update.telefones(), "BR"));
        }
        return this.clienteRepository.save(newobj);
    }

    @Transactional
    public Cliente update(Cliente obj, LoginRequestDTO update) {
        Cliente newobj = this.findByCpf(obj.getCpf());
        newobj.setPassword(argon2Encoder.encode(update.password()));
        return this.clienteRepository.save(newobj);
    }

    public void addTelefones(String cpf, Set<String> telefones) {
        Cliente cliente = this.findByCpf(cpf);
        TelefoneUtil.validarTelefones(telefones, "BR");
        cliente.getTelefones().addAll(TelefoneUtil.formatarTelefones(telefones, "BR"));
        this.clienteRepository.save(cliente);
    }

    public void deleteCliente(String cpf) {
        Cliente cliente = this.findByCpf(cpf);
        this.clienteRepository.delete(cliente);
    }

    public void deleteTelefones(String cpf, Set<String> telefones) {
        Cliente cliente = this.findByCpf(cpf);
        TelefoneUtil.validarTelefones(telefones, "BR");
        cliente.getTelefones().removeAll(TelefoneUtil.formatarTelefones(telefones, "BR"));
        this.clienteRepository.save(cliente);
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequest, Cliente cliente) {
        Argon2Encoder argon2Encoder = new Argon2Encoder();
        if (argon2Encoder.matches(loginRequest.password(), cliente.getPassword())) {
            return true;
        } else {
            throw new InvalidCredentialsException("Usuário ou senha incorreto(s)");
        }
    }

        public void hasPermision(String login, JwtAuthenticationToken token) {
        Cliente obj = this.findByCpfOrEmail(login);
        if (obj.getCpf().equals(token.getName())) {
            return;
        } else {
            throw new PermissionException("Sem permissão para executar esta ação");
        }
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

}
