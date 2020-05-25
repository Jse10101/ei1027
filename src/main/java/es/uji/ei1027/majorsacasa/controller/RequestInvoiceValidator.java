package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.RequestInvoice;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestInvoiceValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return RequestInvoice.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RequestInvoice requestInvoice = (RequestInvoice) obj;
        if (requestInvoice.getIdNumber_request().equals("") || requestInvoice.getIdNumber_invoice().length() > 8)
            errors.rejectValue("idNumber_request", "obligatori",
                    "Cal introduir un ID valid");

        if (requestInvoice.getIdNumber_invoice().equals("") || requestInvoice.getIdNumber_invoice().length() > 8)
            errors.rejectValue("idNumber_invoice", "obligatori",
                    "Cal introduir un ID valid");

    }
}
