package com.johncrisanto.shoestore.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.johncrisanto.shoestore.entity.security.PasswordResetToken;
import com.johncrisanto.shoestore.service.UserService;
import com.johncrisanto.shoestore.service.impl.UserSecurityService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired UserSecurityService userSecurityService;
	
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
	
//	@RequestMapping(value="/newAccount", method=RequestMethod.POST)
//	public String newAccountPost(HttpServletRequest request, @ModelAttribute("email") String userEmail, @ModelAttribute("username") String username, Model model) throws Exception {
//		model.addAttribute("classActiveNewAccount", true);
//		model.addAttribute("email", userEmail);
//		model.addAttribute("username", username);
//		
//		if(userService.findByUsername(username) != null) {
//			model.addAttribute("usernameExists", true);
//			return "myAccount";
//		}
//		
//		if(userService.findByEmail(userEmail) != null) {
//			model.addAttribute("email", true);
//			return "myAccount";
//		}
//		
//		User user = new User();
//		user.setUsername(username);
//
//		return null;
//	}
	
	@RequestMapping("/newAccount")
	public String newAccount(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);
		if(passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}
		
		// If token is not null
		// Retrieve the user
		User user = (User) passToken.getUser();
		
		// Retrieve username
		String username = user.getUsername();
		
		// Get user details using userSecurityService
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		
		// Define authentication environment and define the authentication using the user details 
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		
		// Retrieve current security context and set authentication to the current user
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

}
