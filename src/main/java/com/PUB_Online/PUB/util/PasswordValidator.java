package com.PUB_Online.PUB.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.PUB_Online.PUB.exceptions.InvalidPasswordException;

@Component
public class PasswordValidator {

    public static void validarSenha(String senha) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*+\\-\\/.,\\{}\\[\\]();:?<>\"'_ ]).+{8,100}$";
        List<Exception> exceptions = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(senha);
        boolean possuiLetra = false;
        boolean possuiNumero = false;
        boolean possuiCaractereEspecial = false;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            if (Character.isLetter(c)) {
                possuiLetra = true;
            } else if (Character.isDigit(c)) {
                possuiNumero = true;
            } else if (isSpecialCharacter(c)) {
                possuiCaractereEspecial = true;
            }
        }
        if (!matcher.matches()) {
            if (senha.length() < 8 || senha.length() > 100){
                exceptions.add(new InvalidPasswordException("A senha deve ter entre 8 e 100 caracteres"));
            }
            if (!possuiNumero) {
                exceptions.add(new InvalidPasswordException("A senha deve conter pelo menos um número"));
            }
            if (!possuiLetra) {
                exceptions.add(new InvalidPasswordException("A senha deve conter pelo menos uma letra"));
            }
            if (!possuiCaractereEspecial) {
                exceptions.add(
                    new InvalidPasswordException(
                        "A senha deve conter pelo menos um caractere especial: ~!@#$%^&*+-/.,{}[]();:?<>\"'_ "));
            }
        }
        if (!exceptions.isEmpty()) {
            throw new InvalidPasswordException(exceptions.toString());
        }
    }

    private static boolean isSpecialCharacter(char c) {
        String caracteresEspeciais = "~!@#$%^&*+-/.,{}[]();:?<>\"'_ ";
        return caracteresEspeciais.contains(String.valueOf(c));
    }

}
