package com.springboot.firstapp.Commands.AuthCommands;

import java.util.HashSet;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AuthCommands {

	@Autowired
    protected AuthService authService;
    
	@Autowired
	protected CachedUserRepository cachedUserRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected JwtService jwtService;
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	protected static final HashSet<String> COUNTRIES_HASH_SET = getCountries();
	
	private static HashSet<String> getCountries(){
		String[] locales = Locale.getISOCountries();
		HashSet<String> set = new HashSet<>();
		for(String code : locales) {
			set.add(new Locale("",code).getDisplayCountry().toLowerCase());
		}
		return set;
	}



	public void printCache() {
		//System.out.println(cachedUserRepository.findAll().get(0));
		for(CachedUser cachedUser : cachedUserRepository.findAll()) {
			System.out.println(cachedUser);
		}
	}

}
