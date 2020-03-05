package es.uji.ei1027.majorsacasa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.dao.ContractDao;
import es.uji.ei1027.majorsacasa.model.Contract;

@Controller
@RequestMapping("/contract")
public class ContractController {

	private ContractDao contractDao;
	
	   @Autowired
	   public void ContractDao(ContractDao contractDao) {
	       this.contractDao=contractDao;
	   }
	   
	   // Operacions: Crear, llistar, actualitzar, esborrar
	   // ...
	   @RequestMapping("/list")
	   public String listContract(Model model) {
	      model.addAttribute("contracts", contractDao.getContracts());
	      return "contract/list";
	   }
	   
	   @RequestMapping(value="/add") 
	   public String addContract(Model model) {
	       model.addAttribute("contract", new Contract());
	       return "contracty/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST)
	   public String processAddSubmit(@ModelAttribute("contract") Contract contract,
	                                   BindingResult bindingResult) {
	        if (bindingResult.hasErrors())
	               return "contract/add";
	        contractDao.addContract(contract);
	        return "redirect:list.html";
	    }
	   
	   @RequestMapping(value="/update/{idNumber}", method = RequestMethod.GET)
	   public String editaContract(Model model, @PathVariable int idNumber) {
	       model.addAttribute("contract", contractDao.getContract(idNumber));
	       return "contract/update"; 
	   }

	   @RequestMapping(value="/update", method = RequestMethod.POST) 
	   public String processUpdateSubmit(
	                           @ModelAttribute("contract") Contract contract, 
	                           BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) 
	            return "contract/update";
	        contractDao.updateContract(contract);
	        return "redirect:list"; 
	   }

	   @RequestMapping(value="/delete/{idNumber}")
	   public String processDelete(@PathVariable int idNumber) {
		   contractDao.deleteContract(idNumber);
	          return "redirect:../list"; 
	   }
}