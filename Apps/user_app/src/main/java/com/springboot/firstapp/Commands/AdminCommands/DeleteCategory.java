package com.springboot.firstapp.Commands.AdminCommands;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeleteCategory extends AdminCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
		  throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("admin", parameters, header);
		if(!filterMsg.equals("You can Pass"))
				return filterMsg;
		
		return deleteCategory(Integer.parseInt(parameters.get("category_id").toString()));
	}
	
	@Transactional
	public String deleteCategory(int id) {
		
		Category category = categoryRepository.findById(id).orElse(null);
		if(category == null)
			return "this id is not found";
		
		//List<User> users = userRepository.findAll();
		Set<User> users = category.getUsers();
		for(User user : users) {
			System.out.println(user.getPreferences().toString());
			deleteUserPref(user,category.getId());
			userRepository.save(user);
			System.out.println(user.getPreferences().toString());
		}
		
		categoryRepository.delete(category);
		
		return "deleted successfully";
	}
	
	public void deleteUserPref(User user, int id) {
		for(Category category : user.getPreferences()) {
			if(category.getId() == id) {
				user.getPreferences().remove(category);
				userRepository.save(user);
				break;
			}
		}
	}
	
	
}
