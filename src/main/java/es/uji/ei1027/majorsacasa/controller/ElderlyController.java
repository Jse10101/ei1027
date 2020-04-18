package es.uji.ei1027.majorsacasa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.dao.ElderlyDao;
import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.dao.LoginDao;

@Controller
@RequestMapping("/elderly")
public class ElderlyController {

	private ElderlyDao elderlyDao;
	private LoginDao loginDao;
	
	   @Autowired
	   public void ElderlyDao(ElderlyDao elderlyDao) {
	       this.elderlyDao=elderlyDao;
	   }
	   
		@Autowired
		public void setLoginDao(LoginDao loginDao) {
			this.loginDao = loginDao;
		}
	   
	   // Operacions: Crear, llistar, actualitzar, esborrar
	   // ...
	   @RequestMapping("/list")
	   public String listElderly(Model model) {
	      model.addAttribute("elderlys", elderlyDao.getElderlys());
	      return "elderly/list";
	   }
	   
	   @RequestMapping(value="/add") 
	   public String addElderly(Model model) {
	       model.addAttribute("elderly", new Elderly());
	       return "elderly/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST)
	   public String processAddSubmit(@ModelAttribute("elderly") Elderly elderly, BindingResult bindingResult) {
		   ElderlyValidator elderlyValidador = new ElderlyValidator();
		   elderlyValidador.validate(elderly, bindingResult);
	        if (bindingResult.hasErrors())
	               return "elderly/add";
	        Login login = new Login(elderly.getDni(), elderly.getUserpwd(), "elderly");
			loginDao.addLogin(login);
	        elderlyDao.addElderly(elderly);
	        return "redirect:../elderly/home";
	    }
	   
	   @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
	   public String editaElderly(HttpSession session, Model model, @PathVariable String dni) {
		   Login login = (Login) session.getAttribute("user");
		   if (login == null) {
				model.addAttribute("user", new Login());
				return "login";
			}
		   
	       model.addAttribute("elderly", elderlyDao.getElderly(dni));
	       return "elderly/update"; 
	   }

	   @RequestMapping(value="/update", method = RequestMethod.POST) 
	   public String processUpdateSubmit(
	                           @ModelAttribute("elderly") Elderly elderly, 
	                           BindingResult bindingResult) {
		   ElderlyValidator elderlyValidador = new ElderlyValidator();
		   elderlyValidador.validate(elderly, bindingResult);
	        if (bindingResult.hasErrors()) 
	            return "elderly/update";
	        elderlyDao.updateElderly(elderly);
	        return "redirect:list"; 
	   }

	   @RequestMapping(value="/delete/{dni}")
	   public String processDelete(HttpSession session, Model model, @PathVariable String dni) {
		   Login login = (Login) session.getAttribute("user");
		   
			if (login == null) {
				model.addAttribute("usuario", new Login());
				return "login";
			}
			
		   elderlyDao.deleteElderly(dni);
	          return "redirect:../list"; 
	   }

	   
	
}
