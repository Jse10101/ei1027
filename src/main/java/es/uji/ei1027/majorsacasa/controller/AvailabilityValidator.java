package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AvailabilityDao;
import es.uji.ei1027.majorsacasa.model.Availability;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityValidator implements Validator {

    @Autowired
    AvailabilityDao availabilityDao = new AvailabilityDao();

    @Override
    public boolean supports(Class<?> cls) {
        return Availability.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Availability availability = (Availability) obj;

        if (availability.getDni_volunteer().equals("") || availability.getDni_volunteer().length() != 9)
            errors.rejectValue("dni_volunteer", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (availability.getFecha()==null)
            errors.rejectValue("fecha", "obligatori",
                    "Cal introduir una fecha");

        LocalDate today = LocalDate.now();
        if(availability.getFecha().isBefore(today)){
            errors.rejectValue("fecha", "fecha", "Ha de ser una data futura");
        }

        if (availability.getBeginingHour()==null)
            errors.rejectValue("beginingHour", "obligatori",
                    "Cal introduir una hora d'inici");

        if (availability.getEndingHour()==null)
            errors.rejectValue("endingHour", "obligatori",
                    "Cal introduir una hora de fi");

        if(!availability.getEndingHour().isAfter(availability.getBeginingHour())){
            errors.rejectValue("endingHour", "horas", "Ha de ser una hora posterior a la d'inici");
        }
    }
}
