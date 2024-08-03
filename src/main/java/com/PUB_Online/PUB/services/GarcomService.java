package com.PUB_Online.PUB.services;

import java.util.List;
import java.util.Optional;


import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.PUB_Online.PUB.models.Garcom;
import com.PUB_Online.PUB.models.Garcom.Role;
import com.PUB_Online.PUB.repositories.GarcomRepository;
import com.PUB_Online.PUB.controllers.dtos.GarcomCreateDTO;
import com.PUB_Online.PUB.controllers.dtos.GarcomUpdateDTO;
import com.PUB_Online.PUB.controllers.dtos.LoginRequestDTO;
import com.PUB_Online.PUB.exceptions.DuplicatedValueException;
import com.PUB_Online.PUB.exceptions.InvalidCredentialsException;
import com.PUB_Online.PUB.exceptions.InvalidUsernameException;
import com.PUB_Online.PUB.exceptions.ObjectNotFoundException;
import com.PUB_Online.PUB.exceptions.PermissionException;
import com.PUB_Online.PUB.util.Argon2Encoder;
import com.PUB_Online.PUB.util.CPFUtil;
import com.PUB_Online.PUB.util.PasswordValidator;
import com.PUB_Online.PUB.util.TelefoneUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class GarcomService {
    
    @Autowired
    private GarcomRepository garcomRepository;

    @Autowired
    private Argon2Encoder argon2Encoder;

    @Transactional
    public Garcom create(Garcom obj) {
        if(this.garcomRepository.findByCpf(CPFUtil.formatarCPF(obj.getCpf())).isPresent()) {
            throw new DuplicatedValueException("CPF em uso");
        }

        //validações
        CPFUtil.validarCPF(obj.getCpf());
        PasswordValidator.validarSenha(obj.getPassword());
        TelefoneUtil.validarTelefones(obj.getTelefones(), "BR");
        if(obj.getUsername().contains("@")) {
            throw new InvalidUsernameException("Username não pode conter '@'");
        }

        //formatações
        obj.setCpf(CPFUtil.formatarCPF(obj.getCpf())); 
        obj.setTelefones(TelefoneUtil.formatarTelefones(obj.getTelefones(), "BR"));

        //encode de senha
        obj.setPassword(argon2Encoder.encode(obj.getPassword()));

        obj.setRole(Role.GARCOM);

        return this.garcomRepository.save(obj);
    }

    public Garcom findByCpf(String cpf) {
        CPFUtil.validarCPF(cpf);
        cpf = CPFUtil.formatarCPF(cpf);
        Optional<Garcom> garcom = this.garcomRepository.findByCpf(cpf);
        if(garcom.isPresent()) {
            return garcom.get();
        } else {
            throw new ObjectNotFoundException("Usuário com o cpf: " + cpf + "não encontrado");
        }
    }

    public Garcom findByUsername(String username) {
        if(username.contains("@")) {
            throw new InvalidUsernameException("Username não pode conter '@'");
        }
        Optional<Garcom> garcom = this.garcomRepository.findByUsername(username);
        if(garcom.isPresent()) {
            return garcom.get();
        } else {
            throw new ObjectNotFoundException("Usuário com o username: " + username + "não encontrado");
        }
    }

    public List<Garcom> findByNome(String nome) {
        List<Garcom> garcom = this.garcomRepository.findByNomeContaining(nome);
        if(garcom.size() > 0) {
            return garcom;
        } else {
            throw new ObjectNotFoundException("Garcom não encontrado");
        }

    }

    public Garcom findByCpfOrUsername(String login) {
        try {
            return this.findByCpf(login);
        } catch (Exception e) {
            try {
                return this.findByUsername(login);
            } catch (Exception ex) {
                throw new ObjectNotFoundException("Login inválido");
            }
        }
    }

    public List<Garcom> findAllGarcoms() {
        List<Garcom> users = garcomRepository.findAll();
        return users;
    }

    public Set<String> findTelefones(String cpf) {
        Garcom garcom = this.findByCpf(CPFUtil.formatarCPF(cpf));
        return garcom.getTelefones();
    }

    @Transactional
    public Garcom update(Garcom obj, GarcomUpdateDTO update) {
        Garcom newobj = this.findByCpf(obj.getCpf());
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
        return this.garcomRepository.save(newobj);
    }

    @Transactional
    public Garcom update(Garcom obj, LoginRequestDTO update) {
        Garcom newobj = this.findByCpf(obj.getCpf());
        newobj.setPassword(argon2Encoder.encode(update.password()));
        return this.garcomRepository.save(newobj);
    }

    public void addTelefones(String cpf, Set<String> telefones) {
        Garcom garcom = this.findByCpf(cpf);
        TelefoneUtil.validarTelefones(telefones, "BR");
        garcom.getTelefones().addAll(TelefoneUtil.formatarTelefones(telefones, "BR"));
        this.garcomRepository.save(garcom);
    }

    public void deleteGarcom(String cpf) {
        Garcom garcom = this.findByCpf(cpf);
        this.garcomRepository.delete(garcom);
    }

    public void deleteTelefones(String cpf, Set<String> telefones) {
        Garcom garcom = this.findByCpf(cpf);
        TelefoneUtil.validarTelefones(telefones, "BR");
        garcom.getTelefones().removeAll(TelefoneUtil.formatarTelefones(telefones, "BR"));
        this.garcomRepository.save(garcom);
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequest, Garcom garcom) {
        Argon2Encoder argon2Encoder = new Argon2Encoder();
        if (argon2Encoder.matches(loginRequest.password(), garcom.getPassword())) {
            return true;
        } else {
            throw new InvalidCredentialsException("Usuário ou senha incorreto(s)");
        }
    }

    public void hasPermision(String login, JwtAuthenticationToken token) {
        Garcom obj = this.findByCpfOrUsername(login);
        if (obj.getCpf().equals(token.getName()) || token.getAuthorities().toString().contains("SCOPE_ADMIN")) {
            return;
        } else {
            throw new PermissionException("Sem permissão para executar esta ação");
        }
    }

    public Garcom fromDTO(@Valid GarcomCreateDTO obj) {
        Garcom garcom = new Garcom();
        garcom.setTelefones(obj.telefones());
        garcom.setCpf(obj.cpf());
        garcom.setUsername(obj.username());
        garcom.setNome(obj.nome());
        garcom.setPassword(obj.password());
        return garcom;
    }

}
