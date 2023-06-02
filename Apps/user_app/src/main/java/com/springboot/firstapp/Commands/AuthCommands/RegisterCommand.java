package com.springboot.firstapp.Commands.AuthCommands;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.AuthService;
import com.springboot.firstapp.Service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;




@NoArgsConstructor
@Service
public class RegisterCommand extends AuthCommands{

	
	
	public String execute(Map<String, Object> body, 
						  Map<String, Object>parameters, 
						  Map<String, String>header) {
			
		//build the user by extracting its details from the body
		User user = User.builder()
							.user_name(body.get("user_name").toString())
							.email(body.get("email").toString())
							.password(body.get("password").toString())
							.country(body.get("country").toString())
							.date(LocalDate.parse((CharSequence) body.get("date")))
							.build();
		
		return registerProc(user);
		
	}
	
	@Transactional
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
		//printCache();
		if(checkForEmptyFields(user).length()!=0)
			return checkForEmptyFields(user); 
		
		//if the mail format is wrong
		if (!user.getEmail().matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) 
            return "Email is not in the correct format";
		
		//the country name is incorrect
		if(!COUNTRIES_HASH_SET.contains(user.getCountry().toLowerCase()))
			return "Please, enter a vaild country name";
		
		//encrypt the password
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		System.out.println(encryptedPassword);
		String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
		
		//calling the register procedure
//		int validRegister = userRepository.register(user.getUser_name(),encryptedPassword,
//								user.getEmail(),user.getCountry().toLowerCase(),user.getDate());
        
		Optional<User> oldUser = userRepository.findByEmail(user.getEmail());
		if(oldUser.isPresent())
			return "Email already exists, try to login";
		
		user.setCountry(user.getCountry().toLowerCase());
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		
				
//		if(validRegister == 0)
//			return "Email already exists, try to login";
		
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
		
	    //printCache();
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
//
	
		
}
