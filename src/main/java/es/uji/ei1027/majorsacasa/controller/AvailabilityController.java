package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AvailabilityDao;
import es.uji.ei1027.majorsacasa.dao.VolunteerDao;
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
import java.time.LocalTime;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    private AvailabilityDao availabilityDao;
    private VolunteerDao volunteerDao;

    @Autowired
    public void setAvailabilityDao(AvailabilityDao availabilityDao) {
        this.availabilityDao = availabilityDao;
    }
    
    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }
    
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

    @RequestMapping(value = "/add")
    public String addAvailability(Model model) {
        model.addAttribute("availability", new Availability());
        return "availability/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("availability") Availability availability,
                                   BindingResult bindingResult) {
       AvailabilityValidator availabilityValidador = new AvailabilityValidator();
        availabilityValidador.validate(availability, bindingResult);
        if (bindingResult.hasErrors())
            return "availability/add";
        availabilityDao.addAvailability(availability);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{fecha}/{dni_volunteer}/{beginingHour}", method = RequestMethod.GET)
    public String editaAvailability(Model model, @PathVariable String fecha,
                                    @PathVariable String dni_volunteer, @PathVariable String beginingHour) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        LocalTime tiempo = LocalTime.parse(beginingHour);
        model.addAttribute("availability", availabilityDao.getAvailability(fecha1, dni_volunteer, tiempo));
        return "availability/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("availability") Availability availability,
            BindingResult bindingResult) {
       AvailabilityValidator availabilityValidador = new AvailabilityValidator();
        availabilityValidador.validate(availability, bindingResult);
        if (bindingResult.hasErrors())
            return "availability/update";
        availabilityDao.updateAvailability(availability);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{fecha}/{dni_volunteer}/{beginingHour}")
    public String processDelete(@PathVariable String fecha,
                                @PathVariable String dni_volunteer, @PathVariable String beginingHour) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        LocalTime tiempo = LocalTime.parse(beginingHour);
        availabilityDao.deleteAvailability(fecha1, dni_volunteer, tiempo);
        return "redirect:../list";
    }

    @RequestMapping(value = "/donaDeAltaAvailability/{dni_volunteer}/{fecha}/{beginingHour}/{endingHour}/{dni_elderly}", method = RequestMethod.GET)
    public String donaDeAltaAvailability(Model model, @PathVariable String fecha, @PathVariable String dni_volunteer, @PathVariable String beginingHour, @PathVariable String endingHour, @PathVariable String dni_elderly) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        LocalTime tiempo_ini = LocalTime.parse(beginingHour);
        LocalTime tiempo_fin = LocalTime.parse(endingHour);
        Availability availability = new Availability(fecha1, tiempo_ini, tiempo_fin, false, dni_volunteer, dni_elderly);
        availabilityDao.donaDeAltaAvailability(availability);
        return "elderly/home";
    }
    
    @RequestMapping("/acompanyament")
    public String acompanyament(HttpSession session, Model model) {
    	model.addAttribute("volunteers", volunteerDao.getVolunteers());
    	model.addAttribute("availabilities", availabilityDao.getAvailabilities());
        return "elderly/acompanyament";
    }

}
