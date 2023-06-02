package com.springboot.firstapp.Commands.AdminCommands;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.springboot.firstapp.Model.Category;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class GetCategories extends AdminCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			  		throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("admin", parameters, header);
		if(!filterMsg.equals("You can Pass"))
			return filterMsg;
		
		return getAllGategories();
	}
	
	public String getAllGategories() {
		Set<String> categories = new HashSet<>();
		for(Category category : categoryRepository.getAllCategories())
			categories.add(category.getName());
		return categories.toString();
	}
}
