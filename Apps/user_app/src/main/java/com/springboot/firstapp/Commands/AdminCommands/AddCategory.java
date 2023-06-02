package com.springboot.firstapp.Commands.AdminCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.Category;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class AddCategory extends AdminCommands{

	
	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			  					  throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("admin", parameters, header);
		if(!filterMsg.equals("You can Pass"))
			return filterMsg;
		
		return addCategory(parameters.get("category").toString());

	}
	
	
	
	@Transactional
	public String addCategory(String name) {
		
		if(name.equals(""))
			return "Please, enter the category name";
			
		if(categoryRepository.findByCategoryName(name.toLowerCase()).isPresent())
			return "This category is already found, please add a new one";
		
		Category category = Category
									.builder()
									.name(name.toLowerCase())
									.build();
		
		categoryRepository.save(category);
		return name+" category is added successfully";
	}

}
