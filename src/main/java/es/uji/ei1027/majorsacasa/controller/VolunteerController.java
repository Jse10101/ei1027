package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AvailabilityDao;
import es.uji.ei1027.majorsacasa.dao.LoginDao;
import es.uji.ei1027.majorsacasa.dao.VolunteerDao;
import es.uji.ei1027.majorsacasa.model.Availability;
import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    private VolunteerDao volunteerDao;
    private LoginDao loginDao;
    private AvailabilityDao availabilityDao;

    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }

    @Autowired
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Autowired
    public void setAvailabilityDao(AvailabilityDao availabilityDao) { this.availabilityDao = availabilityDao; }

    @RequestMapping("/home")
    public String homeVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/volunteer/home");
            return "login";
        }

        Volunteer volunteer = (Volunteer) session.getAttribute("volunteer");
        if(login.getRole().equals("volunteer") && login.getUsuario().equals(volunteer.getDni())) {
            if (volunteer.getAccepted() == false){
                return "volunteer/enEspera";
            }
            return "volunteer/home";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "volunteer/home");
        return "login";
    }

    @RequestMapping("/horaris")
    public String horarisVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/volunteer/home");
            return "login";
        }

        Volunteer volunteer = (Volunteer) session.getAttribute("volunteer");
        model.addAttribute("availability", new Availability());
        model.addAttribute("availabilities", availabilityDao.getAvailabilitiesVolunteer(volunteer));
        if(login.getRole().equals("volunteer") && login.getUsuario().equals(volunteer.getDni())) {
            return "volunteer/horaris";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "volunteer/horaris");
        return "login";
    }

    @RequestMapping("/profileVolunteer")
    public String profileVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        Volunteer volunteer = (Volunteer) session.getAttribute("volunteer");
        if(login.getRole().equals("volunteer") && login.getUsuario().equals(volunteer.getDni())) {
            return "volunteer/profileVolunteer";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "volunteer/profileVolunteer");
        return "login";

    }

    @RequestMapping("/ajuda")
    public String ajudaVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        Volunteer volunteer = (Volunteer) session.getAttribute("volunteer");
        if(login.getRole().equals("volunteer") && login.getUsuario().equals(volunteer.getDni())) {
            return "volunteer/ajuda";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "volunteer/ajuda");
        return "login";
    }
    //////////////////////////
    //Esto es lo que ya estaba
    @RequestMapping(value="/add")
    public String addVolunteer(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer, BindingResult bindingResult, HttpSession session) {
        VolunteerValidator volunteerValidator = new VolunteerValidator();
        volunteerValidator.validate(volunteer, bindingResult);
        if (bindingResult.hasErrors()) {
            return "volunteer/add";
        }

        List<Login> listaLogins = loginDao.getLogins();
        for(Login log : listaLogins) {
            if(log.getUsuario().equals(volunteer.getDni())) {
                //DNI ya registrado
                return "volunteer/add";
            }
        }
        Login login = new Login(volunteer.getDni(), volunteer.getPwd(), "volunteer");
        loginDao.addLogin(login);
        volunteerDao.addVolunteerRegister(volunteer);
        Volunteer new_volunteer = volunteerDao.getVolunteer(login.getUsuario());
        session.setAttribute("volunteer", new_volunteer);
        return "redirect:../volunteer/home";
    }
    ///////////CREADO NUEVO para que el VOLUNTEER edite sus cosas:
    @RequestMapping(value="/updateVolunteer")
    public String updateVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        Volunteer volunteer = (Volunteer) session.getAttribute("volunteer");
        model.addAttribute("volunteer_update", volunteer);
        if(login.getRole().equals("volunteer") && login.getUsuario().equals(volunteer.getDni())) {
            return "volunteer/update";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "volunteer/update");
        return "login";
    }

    @RequestMapping(value="/updateVolunteer", method = RequestMethod.POST)
    public String processUpdateSubmitVolunteer(HttpSession session, @ModelAttribute("volunteer_update") Volunteer volunteer) {
        //Solucion rapida para fallo edad menor de 18 y numero que no es longitud = 0
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(volunteer.getBirthDate(), today);
        if(years < 18 || volunteer.getPhoneNumber().length() != 9) {
            return "redirect:/volunteer/profileVolunteer";
        }

        // Si no hay nada mal, funcionara
        volunteerDao.updateParaVolunteer(volunteer);
        session.setAttribute("volunteer", volunteer);
        return "redirect:/volunteer/profileVolunteer";
    }
    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listVolunteers(Model model) {
        model.addAttribute("volunteers", volunteerDao.getVolunteers());
        return "volunteer/list";
    }

    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editaVolunteer(Model model, @PathVariable String dni) {
        model.addAttribute("volunteer", volunteerDao.getVolunteer(dni));
        return "volunteer/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("volunteer") Volunteer volunteer,
            BindingResult bindingResult) {
        VolunteerValidator voluntarioValidador = new VolunteerValidator();
        voluntarioValidador.validate(volunteer, bindingResult);
        if (bindingResult.hasErrors())
            return "volunteer/update";
        volunteerDao.updateVolunteer(volunteer);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        volunteerDao.deleteVolunteer(dni);
        return "redirect:../list";
    }
}
