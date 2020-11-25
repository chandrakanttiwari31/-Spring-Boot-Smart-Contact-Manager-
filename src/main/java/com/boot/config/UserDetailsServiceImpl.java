package com.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.boot.dao.UserRepository;
import com.boot.entities.User;

public class UserDetailsServiceImpl  implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//fetching user from database
	User user = userRepository.getUserByUserNmae(username);
	
	if(user==null)
	{
		
		throw new UsernameNotFoundException("could not found user");
	}
	
	CustomUserDetails c=new CustomUserDetails(user);
		
		return c;
	}

}
