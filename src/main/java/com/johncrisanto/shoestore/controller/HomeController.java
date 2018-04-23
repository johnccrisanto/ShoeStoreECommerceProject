package com.johncrisanto.shoestore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;
import com.johncrisanto.shoestore.entity.security.Role;
import com.johncrisanto.shoestore.entity.security.UserRole;
import com.johncrisanto.shoestore.service.ShoeService;
import com.johncrisanto.shoestore.service.UserService;
import com.johncrisanto.shoestore.service.impl.UserSecurityService;
import com.johncrisanto.shoestore.utility.MailConstructor;
import com.johncrisanto.shoestore.utility.SecurityUtility;

@Controller
public class HomeController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private ShoeService shoeService;

	@RequestMapping("/")
	public String HomePage() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/processLogin")
	public String processLogin() {
		return "redirect:/login";
	}
	
	@RequestMapping("/shoeRack")
	public String shoeRack(Model model) {
		List<Shoe> shoeList = shoeService.findAll();
		model.addAttribute("shoeList", shoeList);
		return "shoeRack";
	}

	@RequestMapping("/forgotDetails")
	public String forgotDetails(HttpServletRequest request, @ModelAttribute("email") String userEmail, Model model) {

		model.addAttribute("classActiveForgotDetails", true);

		User user = userService.findByEmail(userEmail);

		if (user == null) {
			model.addAttribute("emailNotExists", true);
			return "myAccount";
		}

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptedPassword);

		userService.save(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(newEmail);

		model.addAttribute("forgotPasswordEmailSent", true);

		return "myAccount";
	}

	@RequestMapping(value = "/newAccount", method = RequestMethod.POST)
	public String newAccountPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, Model model) throws Exception {
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			return "myAccount";
		}

		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			return "myAccount";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);

		model.addAttribute("emailSent", true);

		return "myAccount";
	}

	@RequestMapping("/newAccount")
	public String newAccount(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);
		if (passToken == null) {
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

		// Define authentication environment and define the authentication using the
		// user details
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		// Retrieve current security context and set authentication to the current user
		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

}
