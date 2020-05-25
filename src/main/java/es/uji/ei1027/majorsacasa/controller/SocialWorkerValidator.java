package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.SocialWorkerDao;
import es.uji.ei1027.majorsacasa.model.SocialWorker;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class SocialWorkerValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return SocialWorker.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        SocialWorker socialWorker = (SocialWorker) obj;
        SocialWorkerDao socialWorkerDao = new SocialWorkerDao();
        List<SocialWorker> listaSociales = socialWorkerDao.getSocialWorkers();

        for(SocialWorker socialista : listaSociales){
            if(socialista.getUserCAS().equals(socialWorker.getUserCAS()))
                errors.rejectValue("userCAS", "obligatori",
                        "Aquest CAS ja existeix");
        }
        if (socialWorker.getName().equals(""))
            errors.rejectValue("name", "obligatori",
                    "Cal introduir un valor");

        if (socialWorker.getUserCAS().equals("") || socialWorker.getUserCAS().length() != 12)
            errors.rejectValue("userCAS", "obligatori",
                    "Cal introduir un CAS vàlid");

        if (socialWorker.getPwd().equals("") || socialWorker.getPwd().length() < 8)
            errors.rejectValue("pwd", "obligatori",
                    "Cal introduir un valor de al menys 8 caracters");

        if (socialWorker.getEmail().equals(""))
            errors.rejectValue("email", "obligatori",
                    "Cal introduir un valor");

        if (socialWorker.getPhoneNumber().equals("") || socialWorker.getPhoneNumber().length() != 9)
            errors.rejectValue("phoneNumber", "obligatori",
                    "Cal introduir un telefon de 9 cifres");
    }
}
