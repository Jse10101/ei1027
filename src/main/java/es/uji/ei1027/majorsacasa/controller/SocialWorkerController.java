package es.uji.ei1027.majorsacasa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.dao.SocialWorkerDao;
import es.uji.ei1027.majorsacasa.model.SocialWorker;

@Controller
@RequestMapping("/socialworker")
public class SocialWorkerController {

	   private SocialWorkerDao socialWorkerDao;

	   @Autowired
	   public void SocialWorkerDao(SocialWorkerDao socialWorkerDao) {
	       this.socialWorkerDao=socialWorkerDao;
	   }

	   // Operacions: Crear, llistar, actualitzar, esborrar
	   // ...
	   @RequestMapping("/list")
	   public String listSocialWorkers(Model model) {
	      model.addAttribute("socialworkers", socialWorkerDao.getSocialWorkers());
	      return "socialworker/list";
	   }
	   
	   @RequestMapping(value="/add") 
	   public String addSocialWorker(Model model) {
	       model.addAttribute("socialworker", new SocialWorker());
	       return "socialworker/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST)
	   public String processAddSubmit(@ModelAttribute("socialworker") SocialWorker socialWorker,
	                                   BindingResult bindingResult) {
	        if (bindingResult.hasErrors())
	               return "socialworker/add";
	        socialWorkerDao.addSocialWorker(socialWorker);
	        return "redirect:list.html";
	    }
	   
	   @RequestMapping(value="/update/{userCAS}", method = RequestMethod.GET)
	   public String editaSocialWorker(Model model, @PathVariable String userCAS) {
	       model.addAttribute("socialworker", socialWorkerDao.getSocialWorker(userCAS));
	       return "socialworker/update"; 
	   }

	   @RequestMapping(value="/update", method = RequestMethod.POST) 
	   public String processUpdateSubmit(
	                           @ModelAttribute("socialworker") SocialWorker socialWorker, 
	                           BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) 
	            return "socialworker/update";
	        socialWorkerDao.updateSocialWorker(socialWorker);
	        return "redirect:list"; 
	   }

	   @RequestMapping(value="/delete/{userCAS}")
	   public String processDelete(@PathVariable String userCAS) {
	          socialWorkerDao.deleteSocialWorker(userCAS);
	          return "redirect:../list"; 
	   }

	   
	
}




