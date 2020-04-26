package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.RequestDao;
import es.uji.ei1027.majorsacasa.model.Request;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Request.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request request = (Request) obj;
        if (request.getIdNumber().equals("") || request.getIdNumber().length()!=5)
            errors.rejectValue("idNumber", "obligatori",
                    "Cal introduir un valor de 5 caracters");

        if (request.getDni_elderly().equals("") || request.getDni_elderly().length() != 9)
            errors.rejectValue("dni_elderly", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (request.getIdNumber_contract().equals("") || request.getIdNumber_contract().length()!=5)
            errors.rejectValue("idNumber_contract", "obligatori",
                    "Cal introduir un valor de 5 caracters");
        
        if (request.getServiceType().equals(""))
            errors.rejectValue("serviceType", "obligatori",
                    "Cal introduir un valor");

        if (request.getCreationDate() == null)
            errors.rejectValue("creationDate", "obligatori",
                    "Cal introduir un valor");

        if (request.getAprovedDate() != null)
            if (!request.getAprovedDate().isAfter(request.getCreationDate()))
                errors.rejectValue("aprovedDate", "posterior",
                        "La fecha d'aprobació te que ser posterior a la de creació");
        if (request.getRejectedDate() != null)
            if (!request.getRejectedDate().isAfter(request.getCreationDate()))
                errors.rejectValue("rejectedDate", "posterior",
                        "La fecha de rebuig te que ser posterior a la de creació");
        if (request.getFinishDate() != null)
            if (!request.getFinishDate().isAfter(request.getCreationDate()))
                errors.rejectValue("finishDate", "posterior",
                        "La fecha finalització te que ser posterior a la de creació");

        if (request.getAprovedDate() != null && request.getRejectedDate() != null){
            errors.rejectValue("rejectedDate", "posterior",
                    "No pot haver-hi fecha de rebuig si hi ha d'aprobació");
        }

        if (request.getFinishDate() != null && request.getRejectedDate() != null){
            errors.rejectValue("finishDate", "posterior",
                    "No pot haver-hi fecha de finalització si hi ha de rebuig");
        }
    }
}
