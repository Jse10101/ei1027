package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Company;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class CompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Company.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Company company = (Company) obj;
        if (company.getName().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Cal introduir un valor");

        if (company.getCIF().equals("") || company.getCIF().length() != 9)
            errors.rejectValue("cif", "obligatori",
                    "Cal introduir un CIF vàlid");

        if (company.getPwd().equals("") || company.getPwd().length() < 8)
            errors.rejectValue("pwd", "obligatori",
                    "Cal introduir un valor de al menys 8 caracters");

        if (company.getAddress().equals(""))
            errors.rejectValue("address", "obligatori",
                    "Cal introduir un valor");

        if (company.getContactPhoneNumber().equals("") || company.getContactPhoneNumber().length() != 9)
            errors.rejectValue("contactPhoneNumber", "obligatori",
                    "Cal introduir un telefon de 9 cifres");

        if (company.getContactEmail().equals(""))
            errors.rejectValue("contactEmail", "obligatori",
                    "Cal introduir un valor");

        if (company.getContactName().equals(""))
            errors.rejectValue("contactName", "obligatori",
                    "Cal introduir un valor");

        if (company.getServiceType().equals(""))
            errors.rejectValue("serviceType", "obligatori",
                    "Cal introduir un valor");

    }
}
