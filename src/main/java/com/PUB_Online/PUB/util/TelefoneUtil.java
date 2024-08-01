package com.PUB_Online.PUB.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.PUB_Online.PUB.exceptions.InvalidTelefoneException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Component
public class TelefoneUtil {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public static void validarTelefones(Set<String> telefones, String regiao) {
        if(telefones != null) {
            for(String item : telefones) {    
                try {
                    PhoneNumber numero = phoneNumberUtil.parse(item, regiao);
                    if(!phoneNumberUtil.isValidNumber(numero)){
                        throw new InvalidTelefoneException("Número de telefone inválido");
                    }
                } catch (NumberParseException e) {
                    throw new InvalidTelefoneException("Número de telefone inválido");
                }
            }
        }
    }

    public static Set<String> formatarTelefones(Set<String> telefones, String regiao) {
        Set<String> telefonesFormatados = new HashSet<String>();
        if(telefones != null) {
            for(String item : telefones) {    
                try {
                    PhoneNumber numero = phoneNumberUtil.parse(item, regiao);
                    telefonesFormatados.add(phoneNumberUtil.format(numero, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
                } catch (NumberParseException e) {
                    return null;
                }
            }
            return telefonesFormatados;
        } else {
            return null;
        }
    }
}
