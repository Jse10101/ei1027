package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.ElderlyDao;
import es.uji.ei1027.majorsacasa.dao.VolunteerDao;
import es.uji.ei1027.majorsacasa.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class VolunteerController {

    private VolunteerDao volunteerDao;

    @Autowired
    public void ElderlyDao(ElderlyDao elderlyDao) {
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
        if (bindingResult.hasErrors())
            return "volunteer/add";
        volunteerDao.addVolunteer(volunteer);
        return "redirect:list.html";
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
