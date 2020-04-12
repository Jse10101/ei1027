package es.uji.ei1027.majorsacasa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.dao.RequestDao;
import es.uji.ei1027.majorsacasa.model.Request;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;

    @Autowired
    public void RequestDao(RequestDao requestDao) {
        this.requestDao=requestDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listRequest(Model model) {
        model.addAttribute("requests", requestDao.getRequests());
        return "request/list";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model) {
        model.addAttribute("request", new Request());
        return "request/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request,
                                   BindingResult bindingResult) {
        RequestValidator requestValidador = new RequestValidator();
        requestValidador.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "request/add";
        requestDao.addRequest(request);
        return "redirect:list.html";
    }

    @RequestMapping(value="/update/{idNumber}", method = RequestMethod.GET)
    public String editaRequest(Model model, @PathVariable String idNumber) {
        model.addAttribute("request", requestDao.getRequest(idNumber));
        return "request/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("request") Request request,
            BindingResult bindingResult) {
        RequestValidator requestValidador = new RequestValidator();
        requestValidador.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "request/update";
        requestDao.updateRequest(request);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idNumber}")
    public String processDelete(@PathVariable String idNumber) {
        requestDao.deleteRequest(idNumber);
        return "redirect:../list";
    }
}
