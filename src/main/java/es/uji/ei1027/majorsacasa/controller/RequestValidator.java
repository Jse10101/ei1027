package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.RequestDao;
import es.uji.ei1027.majorsacasa.model.Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        RequestDao requestDao = new RequestDao();
        System.out.println(requestDao.getRequests());
        //List<Request> listaRequests = requestDao.getRequests();

        if (request.getIdNumber().equals("") || request.getIdNumber().length() > 8)
            errors.rejectValue("idNumber", "obligatori",
                    "Cal introduir un valor correcte");

        /*for(Request peticion : listaRequests){
            if(peticion.getDni_elderly().equals(request.getDni_elderly()) && peticion.getServiceType().equals(request.getServiceType()))
                errors.rejectValue("serviceType", "obligatori",
                        "No es por tindre dos serveis iguals");
            if(peticion.getIdNumber().equals(request.getIdNumber()))
                errors.rejectValue("idNumber", "obligatori",
                        "Aquesta ID ja existeix");

        }*/

        if (request.getDni_elderly().equals("") || request.getDni_elderly().length() != 9)
            errors.rejectValue("dni_elderly", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (request.getIdNumber_contract().equals("") || request.getIdNumber_contract().length() > 8)
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
                        "La data d'aprobació te que ser posterior a la de creació");
        if (request.getRejectedDate() != null)
            if (!request.getRejectedDate().isAfter(request.getCreationDate()))
                errors.rejectValue("rejectedDate", "posterior",
                        "La data de rebuig te que ser posterior a la de creació");
        if (request.getFinishDate() != null)
            if (!request.getFinishDate().isAfter(request.getCreationDate()))
                errors.rejectValue("finishDate", "posterior",
                        "La data finalització te que ser posterior a la de creació");

        if (request.getAprovedDate() != null && request.getRejectedDate() != null) {
            errors.rejectValue("rejectedDate", "posterior",
                    "No pot haver-hi data de rebuig si hi ha d'aprobació");
        }

        if (request.getFinishDate() != null && request.getRejectedDate() != null) {
            errors.rejectValue("finishDate", "posterior",
                    "No pot haver-hi data de finalització si hi ha de rebuig");
        }
    }
}
