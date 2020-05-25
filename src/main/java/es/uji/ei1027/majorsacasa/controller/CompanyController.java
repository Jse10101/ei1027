package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.dao.CompanyDao;
import es.uji.ei1027.majorsacasa.dao.ContractDao;
import es.uji.ei1027.majorsacasa.dao.LoginDao;
import es.uji.ei1027.majorsacasa.dao.RequestDao;
import es.uji.ei1027.majorsacasa.model.Company;
import es.uji.ei1027.majorsacasa.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private CompanyDao companyDao;
    private LoginDao loginDao;
    private RequestDao requestDao;
    private ContractDao contractDao;

    @Autowired
    public void CompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }
    @Autowired
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }
    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }
    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }
    

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    //listar
    @RequestMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyDao.getCompanies());
        return "company/list";
    }

    //afegir
    @RequestMapping(value = "/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }

    //afegir
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("company") Company company,
                                   BindingResult bindingResult) {
        CompanyValidator companyValidador = new CompanyValidator();
        companyValidador.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/add";
        companyDao.addCompany(company);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{cif}", method = RequestMethod.GET)
    public String editaCompany(Model model, @PathVariable String cif) {
        model.addAttribute("company", companyDao.getCompany(cif));
        return "company/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("company") Company company,
            BindingResult bindingResult) {
        CompanyValidator companyValidador = new CompanyValidator();
        companyValidador.validate(company, bindingResult);
        if (bindingResult.hasErrors())
            return "company/update";
        companyDao.updateCompany(company);
        return "redirect:list";
    }


    @RequestMapping(value = "/delete/{cif}")
    public String processDelete(@PathVariable String CIF) {
        companyDao.deleteCompany(CIF);
        return "redirect:../list";
    }


    @RequestMapping(value = "/afegirCompany", method = RequestMethod.POST)
    public String afegirCompany(@ModelAttribute("companyy") Company companyy,
                                   BindingResult bindingResult) {
        CompanyValidator companyValidador = new CompanyValidator();
        companyValidador.validate(companyy, bindingResult);
        if (bindingResult.hasErrors())
            return "redirect:../socialworker/menuCompany";

        List<Login> listaLogins = loginDao.getLogins();
        for(Login log : listaLogins) {
            if(log.getUsuario().equals(companyy.getCif())) {
                //El DNI ya está registrado
                return "redirect:../socialworker/menuCompany";
            }
        }
        Login login = new Login(companyy.getCif(), companyy.getPwd(), "company");
        loginDao.addLogin(login);

        companyDao.addCompany(companyy);
        return "redirect:../socialworker/menuCompany";
    }


    @RequestMapping("/home")
    public String homeCompany(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/company/home");
            return "login";
        }


        Company company = new Company(companyDao.getCompany(login.getUsuario()));
        session.setAttribute("company", company);

        return "company/home";

    }


    @RequestMapping("/profileCompany")
    public String profileCompany(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");
        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }
        if(login.getRole().equals("company")) {
            return "company/profileCompany";
        }
        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "company/profileCompany");
        return "login";
    }

    @RequestMapping("/ajuda")
    public String ajudaCompany(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }


        if(login.getRole().equals("company")) {
            return "company/ajuda";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "company/ajuda");
        return "login";
    }
    
    @RequestMapping("/serveis")
    public String serveisCompany(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");

        if (login == null) {
            model.addAttribute("login", new Login());
            session.setAttribute("nextUrl", "/login");
            return "login";
        }


        if(login.getRole().equals("company")) {
            model.addAttribute("requests", requestDao.getRequests());
            model.addAttribute("contracts", contractDao.getContracts());
            return "company/serveis";
        }

        session.invalidate();
        model.addAttribute("login", new Login());
        session.setAttribute("nextUrl", "company/serveis");
        return "login";
    }
}