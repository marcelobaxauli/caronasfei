package com.caronasfei.web.controller.login.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginJSPController {

	@GetMapping("/login")
	public String login() {
		
		return "/login/index";
	}

}
