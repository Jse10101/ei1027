package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.VolunteerDao;
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

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    private VolunteerDao volunteerDao;

    @Autowired
    public void VolunteerDao(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listVolunteers(Model model) {
        model.addAttribute("volunteers", volunteerDao.getVolunteers());
        return "volunteer/list";
    }

    @RequestMapping(value="/add")
    public String addVolunteer(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("volunteer") Volunteer volunteer,
                                   BindingResult bindingResult) {
        VolunteerValidator voluntarioValidador = new VolunteerValidator();
        voluntarioValidador.validate(volunteer, bindingResult);
        if (bindingResult.hasErrors())
            return "volunteer/add";
        volunteerDao.addVolunteer(volunteer);
        return "redirect:list";
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

    @RequestMapping("/home")
    public String homeVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/volunteer/home");
            return "login";
        }

        Volunteer volunteer = new Volunteer(volunteerDao.getVolunteer(login.getUsuario()));
        session.setAttribute("volunteer", volunteer);
        //session.setAttribute("login", login);
        return "volunteer/home";

    }

    @RequestMapping("/profileVolunteer")
    public String profileVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        return "volunteer/profileVolunteer";
    }

    ///////////CREADO NUEVO para que el VOLUNTEER edite sus cosas:
    @RequestMapping(value="/updateVolunteer")
    public String updateElderly(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        return "elderly/update";
    }

    @RequestMapping(value="/updateVolunteer", method = RequestMethod.POST)
    public String processUpdateSubmitElderly(@ModelAttribute("volunteer") Volunteer volunteer) {
        volunteerDao.updateParaVolunteer(volunteer);
        return "redirect:/elderly/home";
    }
    //////////////////////////

}
