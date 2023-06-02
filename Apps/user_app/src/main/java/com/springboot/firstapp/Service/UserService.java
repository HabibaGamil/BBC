package com.springboot.firstapp.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.query.Procedure;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	 
	 String updatePassword(Long id, String oldPassword, String newPassword); 
	 
	 String updateEmail(Long id, String newEmail);
	 
	 String changeCountry(Long id, String newCountry);
	 
	 String changeUsername(Long id, String newUsername);
	 
	 String addPreference(Long user_id, int pref_id);
	 
	 String deletePreference(Long user_id, int pref_id);
	 
	 Set<Category> getAllPreferences(Long user_id);
	 
	 String deleteAccount(Long user_id);
	 
	 User getMyProfile(Long user_id);
	 
}
