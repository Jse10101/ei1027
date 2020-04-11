package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.RequestInvoiceDao;
import es.uji.ei1027.majorsacasa.model.RequestInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/request_invoice")
public class RequestInvoiceController {

    private RequestInvoiceDao requestInvoiceDao;

    @Autowired
    public void RequestInvoiceDao(RequestInvoiceDao requestInvoiceDao) {
        this.requestInvoiceDao=requestInvoiceDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listRequestInvoice(Model model) {
        model.addAttribute("requests_invoices", requestInvoiceDao.getRequestInvoices());
        return "request_invoice/list";
    }

    @RequestMapping(value="/add")
    public String addRequestInvoice(Model model) {
        model.addAttribute("request_invoice", new RequestInvoice());
        return "request_invoice/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request_invoice") RequestInvoice requestInvoice,
                                   BindingResult bindingResult) {
        RequestInvoiceValidator requestInvoiceValidator = new RequestInvoiceValidator();
        requestInvoiceValidator.validate(requestInvoice, bindingResult);
        if (bindingResult.hasErrors())
            return "request_invoice/add";
        requestInvoiceDao.addRequestInvoice(requestInvoice);
        return "redirect:list.html";
    }

    @RequestMapping(value="/update/{idNumber_invoice}/{idNumber_request}", method = RequestMethod.GET)
    public String editaRequest(Model model, @PathVariable int idNumber_invoice, @PathVariable int idNumber_request) {
        model.addAttribute("request_invoice", requestInvoiceDao.getRequestInvoice(idNumber_invoice, idNumber_request));
        return "request_invoice/update";
    }


    @RequestMapping(value="/delete/{idNumber_invoice}/{idNumber_request}")
    public String processDelete(@PathVariable int idNumber_invoice, @PathVariable int idNumber_request) {
        requestInvoiceDao.deleteRequestInvoice(idNumber_invoice, idNumber_request);
        return "redirect:../list";
    }
}
