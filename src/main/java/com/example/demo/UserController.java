package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller	// This means that this class is a Controller
@RequestMapping(path="/users") // This means URL's start with /users (after Application path)
public class UserController {
	@Autowired // This means to get the bean called userRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;

	// http://localhost:8080/users/add
	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (
		@RequestParam String name
			, @RequestParam String email
			, @RequestParam String password) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		n.setPassword(password);;
		userRepository.save(n);
		return "Saved";
	}
/**
	@PostMapping(path="/login") // Map ONLY POST Requests
	public @ResponseBody String loginUser (
			 @RequestParam String email
			, @RequestParam String password) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		n.setPassword(password);;
		userRepository.save(n);
		return "Saved";
	}
*/
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	@GetMapping(path="/user")
	public @ResponseBody Optional<User> getOneUser(@RequestParam Integer id) {
		// This returns a JSON or XML with the users
		return userRepository.findById(id);
		
	}

	@GetMapping(path="/userByName")
	public @ResponseBody User getOneUserByName(@RequestParam String name) {
		return userRepository.findByName(name);
	}

	
	@GetMapping(path="/userByEmail")
	public @ResponseBody User getOneUserByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}
	@GetMapping(path="/addUser")
	public ModelAndView showPage(){
		return new ModelAndView("signupForm");
	}
}