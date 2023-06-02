package com.springboot.firstapp.Service.Impl;

import java.time.LocalDate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.AuthService;

import lombok.Data;
import lombok.RequiredArgsConstructor;



@Service
@Data
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final CachedUserRepository cachedUserRepository;
	
	private final UserRepository userRepository;
	
	private final  PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	
	
	@Override
	public String registerProc(User user) {
		/*
		 * 1-check for empty fields
		 * 2-check for email format
		 * 3-encrypt the password
		 * 4-call the procedure
		 * 5-if result is 0, then the user already registered
		 * 6-else the user registered successfully
		 * */
		//if there is an empty field, tell me which one
		printCache();
		if(checkForEmptyFields(user).length()!=0)
			return checkForEmptyFields(user); 
		
		//if the mail format is wrong
		if (!user.getEmail().matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) 
            return "Email is not in the correct format";
		
		//encrypt the password
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
		
		//calling the register procedure
		int validRegister = userRepository.register(user.getUser_name(),encryptedPassword,
								user.getEmail(),user.getCountry().toLowerCase(),user.getDate());
				
		if(validRegister == 0)
			return "Email already exists, try to login";
		
		//save the token in the token table
		User savedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
		
		saveToken(accessToken,refreshToken,savedUser);
		
		//System.out.println(token2.getToken());
		return "Welcome "+user.getUser_name()+" to BBC, you're registerd successfully " +"\n"
				+"accessToken "+ accessToken + "\n" + "refreshToken "+ refreshToken;

	}
	
	@Transactional
	public void saveToken(String accessToken, String refreshToken ,User user) {
		
		CachedUser cachedUser = CachedUser.builder()
											.userEmail(user.getEmail())
											.userDetails(user)
											.accessToken(accessToken)
											.refreshToken(refreshToken)
											.build();
		cachedUserRepository.save(cachedUser);
		System.out.println(cachedUser);
		
	    printCache();
	}
	
	public String checkForEmptyFields(User user) {
		String username = user.getUser_name();
		String mail = user.getEmail();
		String password = user.getPassword();
		String country = user.getCountry();
		LocalDate birthDate = user.getDate();
		if(username == null || username.equals(""))
			return "username cannot be empty";
		if(mail == null || mail.equals(""))
			return "mail cannot be empty";
		if(password == null || password.equals(""))
			return "password cannot be empty";
		if(country == null || country.equals(""))
			return "country cannot be empty";
		if(birthDate == null || birthDate.equals(""))
			return "birthDate cannot be empty";
		return "";
	}
		
	@Override
	@Transactional
	public String login(String email, String password) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (Exception e) {
			return "Wrong credentials ";
		}
		CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(email);
				
		//printCache();
		
		//we will generate a new token in two cases
		//1-if the token is already expired
		//2-the token is not expired, but the user performed logged out, so token.isEpired() = true
		String accesstoken = "";
		String refreshToken = "";
		if(cachedUser == null) {
			User user = userRepository.findByEmail(email).orElseThrow();
			accesstoken = jwtService.generateAccessToken(user); 
			refreshToken = jwtService.generateRefreshToken(user);
					cachedUser = CachedUser.builder()
									.userEmail(email)
									.userDetails(user)
									.accessToken(accesstoken)
									.refreshToken(refreshToken)
									.build();
			cachedUserRepository.save(cachedUser);
			return "Successfull login"+"\n"+"accessToken : "+accesstoken + "\n" +"refreshToken : " + refreshToken;
		}
		
		//the user did not log out, however the stored token is expired
		if(cachedUser != null ){
			
			if(!jwtService.isTokenExpired(cachedUser.getAccessToken()))
					return "you are already logged in";
			if(!jwtService.isTokenExpired(cachedUser.getRefreshToken()))
				return "you have to refresh your access Token";
			
			accesstoken = jwtService.generateAccessToken(cachedUser.getUserDetails());
			refreshToken = jwtService.generateRefreshToken(cachedUser.getUserDetails());
			cachedUser.setAccessToken(accesstoken);
			cachedUser.setRefreshToken(refreshToken);
			cachedUserRepository.save(cachedUser);
			return"Successfull login"+"\n"+"accessToken : "+accesstoken + "\n" +"refreshToken : " + refreshToken;
		}
		
		
			
			return "you're already logged in";
	}
	
	public String refreshToken(String refreshToken) {
			
			final String userEmail;
			if(refreshToken == "") {
				return "no token";
			}
			
			System.out.println("goaaaaaaaaaaaaaaa");
			
			// if the refresh token is expired, then the user needs to login again
			if(jwtService.isTokenExpired(refreshToken))
				return "you have to login";
			
			//extract the user email from that token 
			userEmail = jwtService.extractUserName(refreshToken);
			
			//if the token contains a user email
			if(userEmail != null) {
				
				CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(userEmail);
				
				//if the refresh token does not exist in the cache
				if(cachedUser == null) {
					return "invalid token";
				}
				
				//get the userDetails object from the cache
				UserDetails userDetails = cachedUser.getUserDetails();
						
				//get the access token from the cache
				String accessToken = cachedUser.getAccessToken();
				if(!jwtService.isTokenExpired(accessToken))
					return "you can use your access token, as it is not expired";
				
				//We have to check that the token is a valid token and not expired, 
				//and also the given token is equivalent to the token found in the cache
				if(jwtService.isValidToken(refreshToken, userDetails) && refreshToken.equals(cachedUser.getRefreshToken())) {
					String newAccessToken = jwtService.generateAccessToken(userDetails);
					cachedUser.setAccessToken(newAccessToken);
					cachedUserRepository.save(cachedUser);
					return "new AccessToken : "+ newAccessToken + "\n" + "refreshToken : "+ refreshToken;
				}
				
			}	
					
			return "token is invalid, it does not contain email";
			
		}
		
	public void printCache() {
		//System.out.println(cachedUserRepository.findAll().get(0));
		for(CachedUser cachedUser : cachedUserRepository.findAll()) {
			System.out.println(cachedUser);
		}
	}


}
