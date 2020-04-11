package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Contract;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class ContractValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Contract.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contract contract = (Contract) obj;

        if (contract.getIdNumber().equals("") || contract.getIdNumber().length() != 5)
            errors.rejectValue("idNumber", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (contract.getCif_company().equals("") || contract.getCif_company().length() != 9)
            errors.rejectValue("cif_company", "obligatori",
                    "Cal introduir un CIF vàlid");

        if (contract.getServiceType().equals(""))
            errors.rejectValue("serviceType", "obligatori",
                    "Cal introduir un valor");

        if (contract.getPrice()==0)
            errors.rejectValue("price", "obligatori",
                    "Cal introduir un telefon de 9 cifres");

        if (contract.getDateBegining()==null)
            errors.rejectValue("dateBegining", "obligatori",
                    "Cal introduir un valor");

        if (contract.getDateEnding()==null)
            errors.rejectValue("dateEnding", "obligatori",
                    "Cal introduir un valor");

        if (!contract.getDateEnding().isAfter(contract.getDateBegining())) {
            errors.rejectValue("dateEnding", "fecha", "La fecha de fi te que ser posterior a la d'inici");
        }
    }
}
