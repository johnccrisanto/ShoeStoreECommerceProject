package com.johncrisanto.shoestore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.johncrisanto.shoestore.entity.CartItem;
import com.johncrisanto.shoestore.entity.Order;
import com.johncrisanto.shoestore.entity.Shoe;
import com.johncrisanto.shoestore.entity.User;
import com.johncrisanto.shoestore.entity.UserBilling;
import com.johncrisanto.shoestore.entity.UserPayment;
import com.johncrisanto.shoestore.entity.UserShipping;
import com.johncrisanto.shoestore.entity.security.PasswordResetToken;
import com.johncrisanto.shoestore.entity.security.Role;
import com.johncrisanto.shoestore.entity.security.UserRole;
import com.johncrisanto.shoestore.service.CartItemService;
import com.johncrisanto.shoestore.service.OrderService;
import com.johncrisanto.shoestore.service.ShoeService;
import com.johncrisanto.shoestore.service.UserPaymentService;
import com.johncrisanto.shoestore.service.UserService;
import com.johncrisanto.shoestore.service.UserShippingService;
import com.johncrisanto.shoestore.service.impl.UserSecurityService;
import com.johncrisanto.shoestore.utility.MailConstructor;
import com.johncrisanto.shoestore.utility.SecurityUtility;
import com.johncrisanto.shoestore.utility.USConstants;

@Controller
public class HomeController {
	// Git Test Comment
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

	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private UserShippingService userShippingService;

	@Autowired 
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;
	
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
	public String shoeRack(Model model, Principal principal) {
		
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}

		List<Shoe> shoeList = shoeService.findAll();
		model.addAttribute("shoeList", shoeList);
		model.addAttribute("activeAll", true);
		return "shoeRack";
	}

	@RequestMapping("/shoeDetails")
	public String showDetails(@PathParam("id") Long id, Model model, Principal principal) {

		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}

		Shoe shoe = shoeService.findById(id);

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Double> sizeList = Arrays.asList(7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0, 10.5, 11.0, 11.5, 12.0, 12.5, 13.0,
				13.5, 14.0, 14.5, 15.0);
		model.addAttribute("shoe", shoe);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("sizeList", sizeList);
		return "shoeDetails";
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

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);

		List<String> stateList = USConstants.listOfUsStatesCode;
		Collections.sort(stateList);

		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		 model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		return "myProfile";
	}

	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();

		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);

		List<String> stateList = USConstants.listOfUsStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		 model.addAttribute("orderList", user.getOrderList());
		return "myProfile";
	}

	@PostMapping("/addNewCreditCard")
	public String addNewCreditCardPost(@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling, Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.updateUserBilling(userBilling, userPayment, user);
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}
	
	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(@RequestParam("id") Long creditCardId, Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);

			List<String> stateList = USConstants.listOfUsStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			List<String> creditCardTypeList = new ArrayList<>();
			creditCardTypeList.add("Visa");
			creditCardTypeList.add("Mastercard");
			creditCardTypeList.add("Discover");
			creditCardTypeList.add("American Express");
			
			model.addAttribute("creditCardTypeList", creditCardTypeList);
			
			List<Integer> monthList = new ArrayList<>();
			monthList.add(1);
			monthList.add(2);
			monthList.add(3);
			monthList.add(4);
			monthList.add(5);
			monthList.add(6);
			monthList.add(7);
			monthList.add(8);
			monthList.add(9);
			monthList.add(10);
			monthList.add(11);
			monthList.add(12);
			
			model.addAttribute("monthList", monthList);
			
			
			
			List<Integer> yearList = new ArrayList<>();
			yearList.add(2018);
			yearList.add(2019);
			yearList.add(2020);
			yearList.add(2021);
			yearList.add(2022);
			yearList.add(2023);
			yearList.add(2024);
			yearList.add(2025);
			
			model.addAttribute("yearList", yearList);
			
			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(@RequestParam("id") Long creditCardId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);

		if (user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			userPaymentService.removeById(creditCardId);

			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}

	}
	
	@PostMapping("/setDefaultPayment")
	public String setDefaultPayment(@RequestParam("defaultUserPaymentId") Long userPaymentId, Principal principal, Model model) {
		
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(userPaymentId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		return "myProfile";
	}

	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);

		UserShipping userShipping = new UserShipping();

		model.addAttribute("userShipping", userShipping);

		List<String> stateList = USConstants.listOfUsStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		 model.addAttribute("orderList", user.getOrderList());
		return "myProfile";
	}
	
	@PostMapping("/addNewShippingAddress")
	public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());

		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}
	
	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(@RequestParam("id") Long shippingAddressId, Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(shippingAddressId);

		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("userShipping", userShipping);

			List<String> stateList = USConstants.listOfUsStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}
	}
	
	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(@RequestParam("id") Long userShippingId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);

		if (user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			userShippingService.removeById(userShippingId);

			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfShippingAddresses", true);

			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());

			return "myProfile";
		}

	}
	
	@PostMapping("/setDefaultShippingAddress")
	public String setDefaultShippingAddress(@RequestParam("defaultShippingAddressId") Long userShippingId, Principal principal, Model model) {
		
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(userShippingId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);

		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("orderList", user.getOrderList());
		
		
		return "myProfile";
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
		model.addAttribute("orderList", user.getOrderList());

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
	
	@PostMapping("/updateUserInfo")
	public String updateUserInfo(
			@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			Model model
			) throws Exception {
		
		User currentUser = userService.findById(user.getId());
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		/*check email already exists*/
		if (userService.findByEmail(user.getEmail())!=null) {
			if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		
		/*check username already exists*/
		if (userService.findByUsername(user.getUsername())!=null) {
			if(userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		
//		update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
				
				return "myProfile";
			}
		}
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		userService.save(currentUser);
		
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);
		
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("listOfCreditCards", true);
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("orderList", user.getOrderList());
		
		return "myProfile";
	}
	
	@RequestMapping("/orderDetail")
	public String orderDetail(
			@RequestParam("id") Long orderId,
			Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		Order order = orderService.findById(orderId);
		
		if(order.getUser().getId()!=user.getId()) {
			return "badRequestPage";
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			UserShipping userShipping = new UserShipping();
			model.addAttribute("userShipping", userShipping);
			
			List<String> stateList = USConstants.listOfUsStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);
			model.addAttribute("orderList", user.getOrderList());
			return "myProfile";
		}
	}

}
