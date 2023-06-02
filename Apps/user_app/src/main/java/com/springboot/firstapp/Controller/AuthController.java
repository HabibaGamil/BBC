package com.springboot.firstapp.Controller;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Service.AdminService;
import com.springboot.firstapp.Service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@EnableCaching
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/register")
	public String userRegister(@RequestBody User user) {
		
		return authService.registerProc(user);
	}
	
	@PostMapping("/login")
	//@Cacheable(value = "token")
	public String login(@RequestParam(name = "email") String email,
						@RequestParam(name = "password") String password) {
		return authService.login(email, password);
	}
	
	@PostMapping("/refreshToken")
	public String refreshToken(@RequestParam("refresh") String refreshToken) {
		return authService.refreshToken(refreshToken);
	}
}
