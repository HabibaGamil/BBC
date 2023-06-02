package com.springboot.firstapp.Service.Impl;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.sound.midi.SysexMessage;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.resps.StreamConsumerFullInfo;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	private final CategoryRepository categoryRepository;
		
	private final CachedUserRepository cachedUserRepository;
	
	//private final CacheManager cacheManager;
	
	private final  PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthServiceImpl authServiceImpl;
	
	
	private static final HashSet<String> COUNTRIES_HASH_SET = getCountries();
	
	
	
	
	@Override
	@Transactional
	public String updatePassword(Long id,String oldPassword, String newPassword) {
		/*
		 * 1-check if the user exists
		 * 2-check if old password is not empty
		 * 3-check if old password is correct
		 * 4-check if new password is not empty
		 * 4-encrypt the new password
		 * 5-set the new password
		 * */
		User user = userRepository.findById(id).orElseThrow(
											() -> new IllegalStateException("User does not exist"));
		
		if(oldPassword.equals(""))
			return "Please, enter the old password";

		if(!passwordEncoder.matches(oldPassword+"",user.getPassword())	)
			return "the old password is incorrect ";
		
		if(newPassword.equals(""))
			return "Please, enter the new password";
		
		String encryptedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encryptedPassword);
		
//		CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
//		cachedUser.setUserDetails(user);
			
		return "Password is updated successfully, please remember to use it the next time";
		
	}
	
	@Override
	@Transactional
	public String updateEmail(Long id, String newEmail) {
		
		authServiceImpl.printCache();

		if(newEmail.equals(""))
			return "Please, enter your email";
		
		//if the mail format is wrong
		if (!newEmail.matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) 
            return "Email is not in the correct format";
		
		User user = userRepository.findById(id).orElse(null);
		if(user != null && newEmail.equals(user.getEmail()))
			return "Please, Enter a new email, this is the old email";
		
		Optional<User> existingUser = userRepository.findByEmail(newEmail);
		if(existingUser.isPresent())
			return "this mail is already used";
		
		cachedUserRepository.deleteCachedUser(user.getEmail());
		user.setEmail(newEmail);

		//System.out.println(cachedUser);
		System.out.println("fooooooooooooq");
		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		authServiceImpl.saveToken(accessToken, refreshToken, user);
		
		return "New email saved."+"\n"+"Remember to use it next time you sign in."+"\n"+
								"your new accessToken "+accessToken + 
								"\n" + "your new refreshToken "+refreshToken;
	}
	
	public static HashSet<String> getCountries(){
		String[] locales = Locale.getISOCountries();
		HashSet<String> set = new HashSet<>();
		for(String code : locales) {
			set.add(new Locale("",code).getDisplayCountry().toLowerCase());
		}
		return set;
	}
	
	@Transactional
	@Override
	public String changeCountry(Long id, String newCountry){
		
		//get the user that has the passed id
		User user = userRepository.findById(id).orElse(null);
		//the user does not exist
		if(user == null)
			return "user does not exist";
		//the user exists, however the field of country is empty
		if(newCountry.equals(""))
			return "Please, enter the country name";
		//the country name is incorrect
		if(!COUNTRIES_HASH_SET.contains(newCountry.toLowerCase()))
			return "Please, enter a vaild country name";
		user.setCountry(newCountry.toLowerCase());
		
		//CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
		//cachedUser.setUserDetails(user);
		
		return "Country Updated successfully";
	}

	@Transactional
	@Override
	public String changeUsername(Long id, String newUsername) {
		
		User user = userRepository.findById(id).orElse(null);
		if(user == null)
			return "user does not exist";
		if(newUsername.equals(""))
			return "user name field can not be empty";
		if(newUsername.equals(user.getUser_name()))
			return "Please, enter a new user name";
		
		user.setUser_name(newUsername);
		
		//CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
		//cachedUser.setUserDetails(user);
		
		return "user name updated successfully";
	}

	
	@Override
	public String addPreference(Long user_id, int pref_id) {

		User user = userRepository.findById(user_id).orElse(null);
		Category category = categoryRepository.findById(pref_id).orElse(null);
		
		if(user == null)
			return "user does not exist";
		if(category == null)
			return "category does not exist";
		if(user.getPreferences().contains(category))
			return "you already added this category before";
		
		user.getPreferences().add(category);
		category.getUsers().add(user);
		userRepository.save(user);
		categoryRepository.save(category);
	
		return "added successfully";
	}

	@Override
	public String deletePreference(Long user_id, int pref_id) {
		
		User user = userRepository.findById(user_id).orElse(null);
		Category category = categoryRepository.findById(pref_id).orElse(null);
		
		if(user == null)
			return "user does not exist";
		if(category == null)
			return "category does not exist";
		
		if(!user.getPreferences().contains(category))
			return "you do not have this preference";
		
		user.getPreferences().remove(category);
		category.getUsers().remove(user);
		userRepository.save(user);
		categoryRepository.save(category);
		
		return "deleted successfully";
	}
		
	@Override
	public Set<Category> getAllPreferences(Long user_id){
		
		User user = userRepository.findById(user_id).orElse(null);
		if(user != null)
			return user.getPreferences();
		return new HashSet<>();
		
	}

	@Override
	public String deleteAccount(Long user_id) {
		User user = userRepository.findById(user_id).orElse(null);
		if(user == null)
			return "user does not exist";
		
		user.getPreferences().clear();
		
		cachedUserRepository.deleteCachedUser(user.getEmail());
		userRepository.deleteById(user_id);
		return "deleted successfully";
	}
	
	@Override
	public User getMyProfile(Long user_id) {
		
		User user = userRepository.findById(user_id).orElse(null);
		return user;
	}
	
	
}
