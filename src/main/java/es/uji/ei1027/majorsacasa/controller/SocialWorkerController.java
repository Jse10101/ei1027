package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.Company;
import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.model.SocialWorker;
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
@RequestMapping("/socialworker")
public class SocialWorkerController {

    private SocialWorkerDao socialWorkerDao;
    private RequestDao requestDao;
    private ElderlyDao elderlyDao;
    private CompanyDao companyDao;
    private VolunteerDao volunteerDao;

    @Autowired
    public void SocialWorkerDao(SocialWorkerDao socialWorkerDao) {
        this.socialWorkerDao = socialWorkerDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setElderlyDao(ElderlyDao elderlyDao) {
        this.elderlyDao = elderlyDao;
    }

    @Autowired
    public void setCompanyDao(CompanyDao companyDao) { this.companyDao = companyDao; }

    @Autowired
    public void setVolunteerDao(VolunteerDao volunteerDao) { this.volunteerDao = volunteerDao; }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listSocialWorkers(Model model) {
        model.addAttribute("socialworkers", socialWorkerDao.getSocialWorkers());
        return "socialworker/list";
    }

    @RequestMapping(value = "/add")
    public String addSocialWorker(Model model) {
        model.addAttribute("socialworker", new SocialWorker());
        return "socialworker/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("socialworker") SocialWorker socialWorker,
                                   BindingResult bindingResult) {
        SocialWorkerValidator socialworkerValidador = new SocialWorkerValidator();
        socialworkerValidador.validate(socialWorker, bindingResult);
        if (bindingResult.hasErrors())
            return "socialworker/add";
        socialWorkerDao.addSocialWorker(socialWorker);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{userCAS}", method = RequestMethod.GET)
    public String editaSocialWorker(Model model, @PathVariable String userCAS) {
        model.addAttribute("socialworker", socialWorkerDao.getSocialWorker(userCAS));
        return "socialworker/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("socialworker") SocialWorker socialWorker,
            BindingResult bindingResult) {
        SocialWorkerValidator socialworkerValidador = new SocialWorkerValidator();
        socialworkerValidador.validate(socialWorker, bindingResult);
        if (bindingResult.hasErrors())
            return "socialworker/update";
        socialWorkerDao.updateSocialWorker(socialWorker);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{userCAS}")
    public String processDelete(@PathVariable String userCAS) {
        socialWorkerDao.deleteSocialWorker(userCAS);
        return "redirect:../list";
    }

    @RequestMapping("/home")
    public String homeSocialworker(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/socialworker/home");
            return "login";
        }

        SocialWorker socialWorker = new SocialWorker(socialWorkerDao.getSocialWorker(login.getUsuario()));
        session.setAttribute("socialworker", socialWorker);
        return "socialworker/home";

    }

    @RequestMapping("/menuElderly")
    public String menuElderly(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }


        if (login.getRole().equals("socialWorker")) {
            model.addAttribute("requests", requestDao.getRequests());
            model.addAttribute("elderlys", elderlyDao.getElderlys());
            return "socialworker/menuElderly";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuElderly");
        return "login";
    }

    @RequestMapping("/approveRequest/{idNumber}")
    public String approveRequest(HttpSession session, Model model, @PathVariable String idNumber){
        Login login = (Login) session.getAttribute("login");
        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        if(login.getRole().equals("socialWorker")){
            requestDao.approveRequest(idNumber);
            return "redirect:../menuElderly";
        }
        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuElderly");
        return "login";
    }

    @RequestMapping("/rejectRequest/{idNumber}")
    public String rejectRequest(HttpSession session, Model model, @PathVariable String idNumber){
        Login login = (Login) session.getAttribute("login");
        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        if(login.getRole().equals("socialWorker")){
            requestDao.rejectRequest(idNumber);
            return "redirect:../menuElderly";
        }
        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuElderly");
        return "login";
    }

    @RequestMapping("/menuCompany")
    public String menuCompany(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }


        if (login.getRole().equals("socialWorker")) {
            model.addAttribute("companies", companyDao.getCompanies());
            Company companyy = new Company();
            model.addAttribute("companyy", companyy);
            return "socialworker/menuCompany";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuCompany");
        return "login";
    }

    @RequestMapping("/menuVolunteer")
    public String menuVolunteer(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }


        if (login.getRole().equals("socialWorker")) {
            model.addAttribute("volunteers", volunteerDao.getVolunteers());
            return "socialworker/menuVolunteer";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuVolunteer");
        return "login";
    }

    @RequestMapping("/approveVolunteer/{dni}")
    public String approveVolunteer(HttpSession session, Model model, @PathVariable String dni){
        Login login = (Login) session.getAttribute("login");
        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        if(login.getRole().equals("socialWorker")){
            volunteerDao.approveVolunteer(dni);
            return "redirect:../menuVolunteer";
        }
        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuVolunteer");
        return "login";
    }

    @RequestMapping("/rejectVolunteer/{dni}")
    public String rejectVolunteer(HttpSession session, Model model, @PathVariable String dni){
        Login login = (Login) session.getAttribute("login");
        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }

        if(login.getRole().equals("socialWorker")){
            volunteerDao.rejectVolunteer(dni);
            return "redirect:../menuVolunteer";
        }
        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "socialworker/menuVolunteer");
        return "login";
    }


}




