package com.PUB_Online.PUB.util;

import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

import com.PUB_Online.PUB.exceptions.InvalidEmailException;

@Component
public class EmailValidator {
    public static void validarEmail(String email) {
     try {
            InternetAddress emailAddrress = new InternetAddress(email);
            emailAddrress.validate();
        } catch (Exception e) {
            throw new InvalidEmailException("Email inválido");
        }
    }
}
