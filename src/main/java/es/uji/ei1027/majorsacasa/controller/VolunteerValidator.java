package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.VolunteerDao;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class VolunteerValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Volunteer.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Volunteer voluntario = (Volunteer) obj;
        VolunteerDao volunteerDao = new VolunteerDao();
        List<Volunteer> listaVolunteer = volunteerDao.getVolunteers();

        for(Volunteer voluntario1 : listaVolunteer){
            if(voluntario1.getDni().equals(voluntario.getDni()))
                errors.rejectValue("dni", "obligatori",
                        "Aquest DNI ja existeix");
        }

        if (voluntario.getName().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Cal introduir un valor");

        if (voluntario.getDni().equals("") || voluntario.getDni().length() != 9)
            errors.rejectValue("dni", "obligatori",
                    "Cal introduir un DNI vàlid");

        if (voluntario.getPwd().equals("") || voluntario.getPwd().length() < 8)
            errors.rejectValue("pwd", "obligatori",
                    "Cal introduir un valor de al menys 8 caracters");

        if (voluntario.getEmail().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Cal introduir un valor");

        if (voluntario.getPhoneNumber().equals("") || voluntario.getPhoneNumber().length() != 9)
            errors.rejectValue("phoneNumber", "obligatori",
                    "Cal introduir un telefon de 9 cifres");

        if (voluntario.getBirthDate()==null)
            errors.rejectValue("birthDate", "obligatori",
                    "Cal introduir un valor");

        //if(voluntario.getAcceptationDate()!=null)
          //  if(!voluntario.getAcceptationDate().isAfter(voluntario.getBirthDate()))
            //    errors.rejectValue("acceptationDate", "menor", "Tens que ser major de 18 anys");

        // Afegeix ací la validació per a Edat >= 18 anys
        Period periodo = Period.between(voluntario.getBirthDate(), LocalDate.now());
        if (periodo.getYears() < 18) {
            errors.rejectValue("birthDate", "menor", "Tens que ser major de 18 anys");
        }
    }
}