package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AvailabilityDao;
import es.uji.ei1027.majorsacasa.model.Availability;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    private AvailabilityDao availabilityDao;

    @Autowired
    public void AvailabilityDao(AvailabilityDao availabilityDao) {
        this.availabilityDao = availabilityDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listAvailabilities(Model model) {
        model.addAttribute("availabilities", availabilityDao.getAvailabilities());
        return "availability/list";
    }

    @RequestMapping(value="/add")
    public String addAvailability(Model model) {
        model.addAttribute("availability", new Availability());
        return "availability/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("availability") Availability availability,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "availability/add";
        availabilityDao.addAvailability(availability);
        return "redirect:list.html";
    }

    @RequestMapping(value="/update/{fecha}/{dni_volunteer}/{beginingHour}", method = RequestMethod.GET)
    public String editaAvailability(Model model, @PathVariable LocalDate fecha,
                                 @PathVariable String dni_volunteer, @PathVariable LocalDate beginingHour) {
        model.addAttribute("availability", availabilityDao.getAvailability(fecha, dni_volunteer, beginingHour));
        return "availability/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("availability") Availability availability,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "availability/update";
        availabilityDao.updateAvailability(availability);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{fecha}/{dni_volunteer}/{beginingHour}")
    public String processDelete( @PathVariable LocalDate fecha,
                                 @PathVariable String dni_volunteer, @PathVariable LocalDate beginingHour) {
        availabilityDao.deleteAvailability(fecha, dni_volunteer, beginingHour);
        return "redirect:../list";
    }

}
