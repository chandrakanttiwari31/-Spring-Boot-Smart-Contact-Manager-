package com.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/index")
	public String getIndexx()

	{
		
		return "normal/dashboard";
	}
}
