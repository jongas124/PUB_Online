package com.PUB_Online.PUB.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PUB_Online.PUB.controllers.dtos.LoginRequestDTO;
import com.PUB_Online.PUB.controllers.dtos.LoginResponseDTO;
import com.PUB_Online.PUB.models.Cliente;
import com.PUB_Online.PUB.services.ClienteService;
import com.PUB_Online.PUB.util.Argon2Encoder;

import lombok.AllArgsConstructor;

@RestController
@Validated
@RequestMapping("/login")
@AllArgsConstructor
public class TokenController {
    private final JwtEncoder jwtEncoder;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Cliente cliente = this.clienteService.findByEmail(loginRequest.login());
    
        if (cliente == null) {
            return this.loginGarcom(loginRequest);
        }

        clienteService.isLoginCorrect(loginRequest, cliente);

        Argon2Encoder argon2Encoder = new Argon2Encoder();

        //verifica se a senha precisa ser recriptografada
        if (argon2Encoder.upgradeEncoding(cliente.getPassword())) {
            cliente.setPassword(argon2Encoder.encode(loginRequest.password()));
            this.clienteService.update(cliente, loginRequest);
        }

        Instant now = Instant.now();
        Long expiresIn = 3000L; // 3000 segundos: 50 minutos

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("PUB_Online")
            .subject(cliente.getCpf().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }

    //desconsiderar, temporário
    public ResponseEntity<LoginResponseDTO> loginGarcom(LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(new LoginResponseDTO("token", 3000L));
    }

}
