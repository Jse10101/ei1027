package es.uji.ei1027.majorsacasa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.dao.LoginDao;


class LoginValidator implements Validator { 
	
	@Override
	public boolean supports(Class<?> cls) { 
		return Login.class.isAssignableFrom(cls);
	}
	
	@Override 
	public void validate(Object obj, Errors errors) {
		Login user = (Login) obj;
		if (user.getUsuario().equals("")) {
			errors.rejectValue("usuario", "obligatori", "Cal introduir un DNI/CIF vàlid");
		}
		if (user.getPwd().equals("") || user.getPwd().length() < 8) {
			errors.rejectValue("pwd", "obligatori", "Cal introduir una contrasenya vàlida");
		}
	}
}
	
@Controller
public class LoginController {
	
	@Autowired
	private LoginDao loginDao;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}
	

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("login") Login login, BindingResult bindingResult, HttpSession session) {
		LoginValidator loginValidator = new LoginValidator(); 
		loginValidator.validate(login, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "redirect:/login";
		}
		
		//Comprova que les dades siguen correctes
		login = loginDao.getLogin(login.getUsuario(),login.getPwd()); 
		if (login == null) {
			bindingResult.rejectValue("pwd", "badpw", "Contraseña incorrecta"); 
			return "redirect:/login";
		}

		session.setAttribute("login", login);
		
		
		//Switch per a saber qui entra en la web
		switch (login.getRole()) {
			case "elderly":
				return "redirect:/elderly/home";
			case "volunteer":
				return "redirect:/volunteer/home";
			case "company":
				return "redirect:/company/home";
			case "socialWorker":
				return "redirect:/socialworker/home";

			//Si es ADMIN entra desde index - REFER -
		}
			
		// Si no, torna a la principal
		return "redirect:/index";
	}
	
	
	   @RequestMapping(value="/update/{usuario}", method = RequestMethod.GET)
	   public String editaElderly(HttpSession session, Model model, @PathVariable String usuario) {
		   
	       model.addAttribute("login", loginDao.getLogin(usuario));
	       return "elderly/update"; 
	   }
	
	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}