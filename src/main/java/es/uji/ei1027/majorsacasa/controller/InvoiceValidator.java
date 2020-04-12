package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Invoice;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class InvoiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Invoice.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Invoice invoice = (Invoice) obj;

        if (invoice.getIdNumber().equals("") || invoice.getIdNumber().length() != 5)
            errors.rejectValue("idNumber", "obligatori",
                    "Cal introduir un ID vàlid");

        if (invoice.getAmount()==0)
            errors.rejectValue("amount", "obligatori",
                    "Cal introduir un valor");

        if (invoice.getElderlyDNI().equals("") || invoice.getElderlyDNI().length() != 9)
            errors.rejectValue("elderlyDNI", "obligatori",
                    "Cal introduir un telefon de 9 cifres");

        if (invoice.getFecha()==null)
            errors.rejectValue("fecha", "obligatori",
                    "Cal introduir un valor");

    }
}