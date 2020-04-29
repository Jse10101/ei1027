package es.uji.ei1027.majorsacasa.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.dao.RequestDao;
import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.model.Request;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
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
    
    /////////nou delete
    @RequestMapping(value="/donaDeBaixaRequest/{idNumber}", method = RequestMethod.GET)
    public String donaDeBaixaRequest(Model model, @PathVariable String idNumber) {
        requestDao.donaDeBaixaRequest(idNumber);
        return "elderly/home";
    }
    
    /////////////////nou add
    @RequestMapping(value="/addRequestElderly", method=RequestMethod.POST)
    public String addRequestElderly(@ModelAttribute("requestt") Request requestt, HttpSession session) {
    	Login login = (Login) session.getAttribute("login");
		if(login.getRole().equals("elderly")) {
			requestt.setDni_elderly(login.getUsuario());
			
			//Comprobamos que la persona no tiene ese servicio ya elegido y está en activo o a la espera
			List<Request> listaRequests = requestDao.getRequests();
	    	
	    	for(Request req : listaRequests) {
	            if(( req.getDni_elderly().equals(requestt.getDni_elderly()) ) && ( req.getServiceType().equals(requestt.getServiceType()) ) && ( req.getState() || req.getAprovedDate()==null && req.getRejectedDate()==null)){
	            	return "elderly/home";
	            }
	        }
			
			
			requestDao.donaDeAltaRequest(requestt);
			return "elderly/home";
		}
		
        return "elderly/home";
    }
    
    //////////////
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("request") Request request) {
        requestDao.updateRequest(request);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idNumber}")
    public String processDelete(@PathVariable String idNumber) {
        requestDao.deleteRequest(idNumber);
        return "redirect:../list";
    }
}
