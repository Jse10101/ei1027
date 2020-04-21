package es.uji.ei1027.majorsacasa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@RequestMapping("/register")
	public String register() {
		
		return "register/register";
	}
}
