
package com.boot.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.dao.UserRepository;
import com.boot.entities.User;
import com.boot.helper.Message;

@Controller

public class HomeController {

	
	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String getHome() {

		return "home";
	}

	@RequestMapping("/signup")
	public String signup(Model m) {

		m.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping("/about")
	public String about() {

		return "about";
	}

	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") 
	boolean agreement, Model m,HttpSession session,BindingResult rr) {
		try {

			if (!agreement) {

				System.out.println("you have not agreed terms and condition");
				throw new Exception("you have not agreed terms and condition");
			}
			
			if(rr.hasErrors())
			{
				System.out.println("Error="+rr.toString());
				m.addAttribute("user",user);
				return "signup";
			}

			user.setRole("user");
			user.setEnabled(true);
			user.setImageurl("default.png");
			
			user.setPassword(PasswordEncoder.encode(user.getPassword()));
			

			System.out.println("agreement=" + agreement);
			System.out.println(user);

			User result = this.userRepository.save(user);

			m.addAttribute("user", new User());

			  session.setAttribute("message", new Message("Successfully Regeistered!!!", "alert-success"));
				return "signup";
			
		} catch (Exception e) {
			
			e.printStackTrace();
		    m.addAttribute("user",user);
		    session.setAttribute("message", new Message("something went wrong!!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}

	
	}
}
