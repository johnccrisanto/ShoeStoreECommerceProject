package com.johncrisanto.shoestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String HomePage() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/forgotDetails")
	public String forgotDetails(Model model) {
		model.addAttribute("classActiveForgotDetails", true);
		return "myAccount";
	}
	
	@RequestMapping("/newAccount")
	public String newAccount(Model model) {
		model.addAttribute("classActiveNewAccount", true);
		return "myAccount";
	}

}
