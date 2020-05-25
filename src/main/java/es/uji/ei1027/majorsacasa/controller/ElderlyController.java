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
import es.uji.ei1027.majorsacasa.dao.ElderlyDao;
import es.uji.ei1027.majorsacasa.model.Elderly;
import es.uji.ei1027.majorsacasa.model.Login;
import es.uji.ei1027.majorsacasa.model.Request;
import es.uji.ei1027.majorsacasa.dao.LoginDao;
import es.uji.ei1027.majorsacasa.dao.RequestDao;

@Controller
@RequestMapping("/elderly")
public class ElderlyController {

	private ElderlyDao elderlyDao;
	private LoginDao loginDao;
	private RequestDao requestDao;
	
	   @Autowired
	   public void setElderlyDao(ElderlyDao elderlyDao) {
	       this.elderlyDao=elderlyDao;
	   }
	   
		@Autowired
		public void setLoginDao(LoginDao loginDao) {
			this.loginDao = loginDao;
		}
		
	    @Autowired
	    public void setRequestDao(RequestDao requestDao) {
	        this.requestDao=requestDao;
	    }
	   
	   // Operacions: Crear, llistar, actualitzar, esborrar
	   // ...
	   @RequestMapping("/list")
	   public String listElderly(Model model) {
			model.addAttribute("elderlys", elderlyDao.getElderlys());
			return "elderly/list";
	   }
	   
	   @RequestMapping("/home")
		public String homeElderly(HttpSession session, Model model) {
			Login login = (Login) session.getAttribute("login");
			
			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/elderly/home");
				return "login";
			}

			Elderly elderly = (Elderly) session.getAttribute("elderly");
		    if(login.getRole().equals("elderly") && login.getUsuario().equals(elderly.getDni())) {
		    	return "elderly/home";
		    }

		    session.invalidate();
		    model.addAttribute("login", new Login());
		    session.setAttribute("nextUrl", "elderly/home");
		    return "login";

		}
	   

	   @RequestMapping("/ajuda")
		public String ajudaElderly(HttpSession session, Model model) {
		    Login login = (Login) session.getAttribute("login");
		   
			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/login");
				return "login";
			}

		    Elderly elderly = (Elderly) session.getAttribute("elderly");
		    if(login.getRole().equals("elderly") && login.getUsuario().equals(elderly.getDni())) {
			    return "elderly/ajuda";
		    }

		    session.invalidate();
		    model.addAttribute("login", new Login());
		    session.setAttribute("nextUrl", "elderly/ajuda");
		    return "login";
		}
	   
	   @RequestMapping("/serveis")
		public String serveisElderly(HttpSession session, Model model) {
		    Login login = (Login) session.getAttribute("login");
		   
			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/login");
				return "login";
			}

		    Elderly elderly = (Elderly) session.getAttribute("elderly");
			if(login.getRole().equals("elderly") && login.getUsuario().equals(elderly.getDni())) {
				model.addAttribute("requests", requestDao.getRequests());
				Request requestt = new Request();
				model.addAttribute("requestt", requestt);
				return "elderly/serveis";
			}
			
			session.invalidate();
			model.addAttribute("login", new Login());
			session.setAttribute("nextUrl", "elderly/serveis");
			return "login";
		}
	   
	   @RequestMapping("/profileElderly")
		public String profileElderly(HttpSession session, Model model) {
		   Login login = (Login) session.getAttribute("login");

			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/login");
				return "login";
			}

		    Elderly elderly = (Elderly) session.getAttribute("elderly");
		   model.addAttribute("requests", requestDao.getRequests());
			if(login.getRole().equals("elderly") && login.getUsuario().equals(elderly.getDni())) {
				return "elderly/profileElderly";
			}
			
			session.invalidate();
			model.addAttribute("login", new Login());
			session.setAttribute("nextUrl", "elderly/profileElderly");
			return "login";
		}
   
	   @RequestMapping(value="/add") 
	   public String addElderly(Model model) {
	       model.addAttribute("elderly", new Elderly());
	       return "elderly/add";
	   }


	   @RequestMapping(value="/add", method=RequestMethod.POST)
	   public String processAddSubmit(@ModelAttribute("elderly") Elderly elderly, BindingResult bindingResult, HttpSession session) {
		    ElderlyValidator elderlyValidator = new ElderlyValidator();
		    elderlyValidator.validate(elderly, bindingResult);
	        if (bindingResult.hasErrors()) {
				return "elderly/add";
			}

	        List<Login> listaLogins = loginDao.getLogins();
	        for(Login log : listaLogins) {
	        	if(log.getUsuario().equals(elderly.getDni())) {
	        		//El DNI ya está registrado
	        		return "elderly/add";
	        	}
	        }
	        Login login = new Login(elderly.getDni(), elderly.getUserpwd(), "elderly");
			loginDao.addLogin(login);
	        elderlyDao.addElderlyRegistro(elderly);
		    Elderly new_elderly = elderlyDao.getElderly(login.getUsuario());
		    session.setAttribute("elderly", new_elderly);
	        return "redirect:/elderly/home";
	    }

		///////////CREADO NUEVO para que el ELDERLY edite sus cosas:
		@RequestMapping(value="/updateElderly")
		public String updateElderly(HttpSession session, Model model) {
			Elderly elderly_update = (Elderly) session.getAttribute("elderly");
			Login login = (Login) session.getAttribute("login");

			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/login");
				return "login";
			}

			model.addAttribute("elderly_update", elderly_update);
			if(login.getRole().equals("elderly") && login.getUsuario().equals(elderly_update.getDni())) {
				return "elderly/update";
			}

			session.invalidate();
			model.addAttribute("login", new Login());
			session.setAttribute("nextUrl", "elderly/updateElderly");
			return "login";
		}

		@RequestMapping(value="/updateElderly", method = RequestMethod.POST)
		public String processUpdateSubmitElderly(HttpSession session, @ModelAttribute("elderly_update") Elderly elderly, Model model, BindingResult bindingResult) {
			ElderlyValidator elderlyValidator = new ElderlyValidator();
			elderlyValidator.validate(elderly, bindingResult);
			if (bindingResult.hasErrors()) {
				return "/elderly/update";
			}

			elderlyDao.updateParaElderly(elderly);
			session.setAttribute("elderly", elderly);
			return "redirect:/elderly/profileElderly";
		}
		//////////////////////////
	   
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
	   public String processUpdateSubmit(@ModelAttribute("elderly") Elderly elderly, BindingResult bindingResult) {
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
