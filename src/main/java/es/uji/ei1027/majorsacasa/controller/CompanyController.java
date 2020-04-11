package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.dao.CompanyDao;
import es.uji.ei1027.majorsacasa.model.Company;
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
@RequestMapping("/company")
public class CompanyController {

    private CompanyDao companyDao;

    @Autowired
    public void CompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyDao.getCompnanies());
        return "company/list";
    }

    @RequestMapping(value = "/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }

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
    public String editaCompany(Model model, @PathVariable String CIF) {
        model.addAttribute("company", companyDao.getCompany(CIF));
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

}
