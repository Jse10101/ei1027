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
	   public void ElderlyDao(ElderlyDao elderlyDao) {
	       this.elderlyDao=elderlyDao;
	   }
	   
		@Autowired
		public void setLoginDao(LoginDao loginDao) {
			this.loginDao = loginDao;
		}
		
		   @Autowired
		   public void RequestDao(RequestDao requestDao) {
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

			if(login.getRole().equals("elderly")) {
				Elderly elderly = new Elderly(elderlyDao.getElderly(login.getUsuario()));
				session.setAttribute("elderly", elderly);
				session.setAttribute("login", login);
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

		   
			if(login.getRole().equals("elderly")) {
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

		   
			if(login.getRole().equals("elderly")) {
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

			if(login.getRole().equals("elderly")) {
				model.addAttribute("requests", requestDao.getRequests());
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
	   public String processAddSubmit(@ModelAttribute("elderly") Elderly elderly, BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) {
	               return "elderly/add";
	        }

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
	   public String processUpdateSubmit(@ModelAttribute("elderly") Elderly elderly, BindingResult bindingResult) {
		   ElderlyValidator elderlyValidador = new ElderlyValidator();
		   elderlyValidador.validate(elderly, bindingResult);
	        if (bindingResult.hasErrors()) 
	            return "elderly/update";
	        elderlyDao.updateElderly(elderly);
	        return "redirect:list"; 
	   }
	   
	   ///////////CREADO NUEVO para que el ELDERLY edite sus cosas:
	   @RequestMapping(value="/updateElderly")
	   public String updateElderly(HttpSession session, Model model) {
		   Login login = (Login) session.getAttribute("login");
		   
			if (login == null) {
				model.addAttribute("login", new Login());
				session.setAttribute("nextUrl", "/login");
				return "login";
			}

		   
			if(login.getRole().equals("elderly")) {
				model.addAttribute("elderly", elderlyDao.getElderly(login.getUsuario()));
				return "elderly/update";
			}
			
			session.invalidate();
			model.addAttribute("login", new Login());
			session.setAttribute("nextUrl", "elderly/update");
			return "login";
		}

	   @RequestMapping(value="/updateElderly", method = RequestMethod.POST) 
	   public String processUpdateSubmitElderly(@ModelAttribute("elderly") Elderly elderly) {
		   elderlyDao.updateParaElderly(elderly);
		   return "redirect:/elderly/home";
	   }
	   //////////////////////////
	   
	   
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
