package com.springboot.firstapp.Controller;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.MediaSize.NA;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Service.AuthService;
import com.springboot.firstapp.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@EnableCaching
public class UserController {

	private final UserService userService;

	
	@PostMapping("/update-password")
	public String updatePassword(@RequestParam(name = "user_id") long id, 
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "newPassword") String newPassword) {
		return userService.updatePassword(id, oldPassword, newPassword);
	}
	
	
	@PostMapping("/update-email")
	public String updateEmail(@RequestParam(name = "user_id") Long id,
							  @RequestParam(name = "newEmail") String newEmail) {
		return userService.updateEmail(id, newEmail);
	}
	
	@PostMapping("/change-country")
	public String changeCountry(@RequestParam(name = "user_id") Long id,
								  @RequestParam(name = "newCountry") String newCountry){
		return userService.changeCountry(id, newCountry);
	}
	
	@PostMapping("/change-username")
	public String changeUsername(@RequestParam(name = "user_id") Long id,
								 @RequestParam(name = "newUsername") String newUsername) {
		return userService.changeUsername(id, newUsername);
	}
	
	@PostMapping("/add-preference")
	public String addPreference(@RequestParam(name = "user_id") Long user_id,
								@RequestParam(name = "pref_id") int pref_id) {
		return userService.addPreference(user_id, pref_id);
	}
	
	@PostMapping("/delete-preference")
	public String deletePreference(@RequestParam(name = "user_id") Long user_id,
									@RequestParam(name = "pref_id") int pref_id) {
		return userService.deletePreference(user_id, pref_id);
	}

	@PostMapping("/get-preferences")
	public Set<Category> getAllPreferences(@RequestParam(name = "user_id") Long usre_id) {
		return userService.getAllPreferences(usre_id);
	}
	
	@PostMapping("/delete-account")
	public String deleteAccount(@RequestParam(name = "user_id") Long user_id) {
		return userService.deleteAccount(user_id);
	}
	
	@GetMapping("/get-profile")
	public User getMyProfile(@RequestParam(name = "user_id") Long user_id) {
		return userService.getMyProfile(user_id);
	}
	
}
