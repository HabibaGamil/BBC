package com.springboot.firstapp.Service;

import com.springboot.firstapp.Model.User;

public interface AuthService {

	String login(String username, String password);
	
	String registerProc(User user);
	
	String refreshToken(String token);
	
}
