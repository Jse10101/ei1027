package es.uji.ei1027.majorsacasa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		if(user.getPassword() == null || user.getUser() == null) {
			ValidationUtils.rejectIfEmpty(errors, "user", "user.empty");
			ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
		}
	}
}
	
@Controller
public class LoginController {
	
	@Autowired
	private LoginDao loginDao;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new Login());
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") Login user,  		
				BindingResult bindingResult, HttpSession session) {
		LoginValidator loginValidator = new LoginValidator(); 
		loginValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}

		//Comprova que les dades siguen correctes
		user = loginDao.getLogin(user.getUser(),user.getPassword()); 
		if (user == null) {
			bindingResult.rejectValue("password", "badpw", "Contraseña incorrecta"); 
			return "login";
		}
		
		//Tot ok i guardem les dades de l'usuari a la sesio
		session.setAttribute("user", user); 
		Object url = session.getAttribute("nextUrl");
		if(url!=null) {
			session.removeAttribute("nextUrl");
			return "redirect:"+url;
			
		}
		
		
		//Switch per a saber qui entra en la web
		switch (user.getRole()) {
			case "elderly":
				return "redirect:/elderly/home";
			case "volunteer":
				return "redirect:/volunteer/home";
			case "company":
				return "redirect:/company/home";
			//Si es ADMIN entra desde index - REFER -
			case "instructor":
				return "redirect:/index";
		}
			
		// Si no, torna a la principal
		return "redirect:/";
	}
	
	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}