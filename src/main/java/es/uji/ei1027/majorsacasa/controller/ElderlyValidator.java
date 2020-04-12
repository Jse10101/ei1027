package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class ElderlyValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Elderly.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Elderly elderly = (Elderly) obj;
        if (elderly.getName().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Cal introduir un valor");
        if (elderly.getSurname().equals(""))
            errors.rejectValue("surname", "obligatori",
                    "Cal introduir un valor");

        if (elderly.getAddress().equals(""))
            errors.rejectValue("surname", "obligatori",
                    "Cal introduir un valor");
        if (elderly.getUserCAS_socialWorker() != null)
            if (elderly.getUserCAS_socialWorker().length() != 12)
                errors.rejectValue("userCAS_socialWorker", "obligatori",
                        "Cal introduir un CAS vàlid");

        if (elderly.getDni().equals("") || elderly.getDni().length() != 9)
            errors.rejectValue("dni", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (elderly.getUserpwd().equals("") || elderly.getUserpwd().length() < 8)
            errors.rejectValue("userpwd", "obligatori",
                    "Cal introduir un valor de al menys 8 caracters");

        if (elderly.getBankAccountNumber().equals("") || elderly.getBankAccountNumber().length() !=19)
            errors.rejectValue("bankAccountNumber", "obligatori",
                    "Cal introduir un valor de 19 caracters");

        if (elderly.getEmail().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Cal introduir un valor");

        if (elderly.getPhoneNumber().equals("") || elderly.getPhoneNumber().length() != 9)
            errors.rejectValue("phoneNumber", "obligatori",
                    "Cal introduir un telefon de 9 cifres");
        if (elderly.getDateCreation() == null)
            errors.rejectValue("dateCreation", "obligatori",
                    "Cal introduir un valor");

        if (elderly.getBirthDate() == null)
            errors.rejectValue("birthDate", "obligatori",
                    "Cal introduir un valor");

        // Afegeix ací la validació per a Edat >= 65 anys
        Period periodo = Period.between(elderly.getBirthDate(), LocalDate.now());
        if (periodo.getYears() < 65) {
            errors.rejectValue("birthDate", "menor", "Tens que estar en l'edat de jubilació");
        }
    }
}

