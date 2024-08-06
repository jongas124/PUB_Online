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
import com.PUB_Online.PUB.models.Garcom;
import com.PUB_Online.PUB.services.ClienteService;
import com.PUB_Online.PUB.services.GarcomService;
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

    @Autowired
    private GarcomService garcomService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        if (!loginRequest.login().contains("@")) {
            return this.loginGarcom(loginRequest);
        }
        Cliente cliente = this.clienteService.findByEmail(loginRequest.login());

        clienteService.isLoginCorrect(loginRequest, cliente);

        Argon2Encoder argon2Encoder = new Argon2Encoder();

        //verifica se a senha precisa ser recriptografada
        if (argon2Encoder.upgradeEncoding(cliente.getPassword())) {
            cliente.setPassword(argon2Encoder.encode(loginRequest.password()));
            this.clienteService.update(cliente, loginRequest);
        }

        Instant now = Instant.now();
        Long expiresIn = 30000L; // 30000 segundos: 500 minutos
        String scope = cliente.getRole().toString();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("PUB_Online")
            .subject(cliente.getCpf().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scope)
            .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }

    public ResponseEntity<LoginResponseDTO> loginGarcom(LoginRequestDTO loginRequest) {
        Garcom garcom = this.garcomService.findByCpfOrUsername(loginRequest.login());

        garcomService.isLoginCorrect(loginRequest, garcom);

        Argon2Encoder argon2Encoder = new Argon2Encoder();

        //verifica se a senha precisa ser recriptografada
        if (argon2Encoder.upgradeEncoding(garcom.getPassword())) {
            garcom.setPassword(argon2Encoder.encode(loginRequest.password()));
            this.garcomService.update(garcom, loginRequest);
        }

        Instant now = Instant.now();
        Long expiresIn = 30000L; // 30000 segundos: 500 minutos
        String scope = garcom.getRole().toString();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("PUB_Online")
            .subject(garcom.getCpf().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scope)
            .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }

}
