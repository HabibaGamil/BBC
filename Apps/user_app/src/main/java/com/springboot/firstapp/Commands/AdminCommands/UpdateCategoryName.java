package com.springboot.firstapp.Commands.AdminCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.Category;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UpdateCategoryName extends AdminCommands{

	
	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
					  throws NumberFormatException, ServletException, IOException {

			String filterMsg = doFilter("admin", parameters, header);
			if(!filterMsg.equals("You can Pass"))
					return filterMsg;
			
			return updateCategoryName(Integer.parseInt(parameters.get("category_id").toString()),
									  parameters.get("new_name").toString());

	}
	
	@Transactional
	public String updateCategoryName(int id,String newName) {

		Category category = categoryRepository.findById(id).orElse(null);
		if(category == null)
			return "this id is not found";
		
		if(newName == null)
			return "Please, this field can not be empty";
		
		if(category.getName().toLowerCase().equals(newName.toLowerCase()))
			return "Please, enter a new name";
		
		if(categoryRepository.findByCategoryName(newName.toLowerCase()).isPresent())
			return "This category is already in the database";
		
		category.setName(newName.toLowerCase());
		categoryRepository.save(category);
		
		return "updated successfully";
	}
	
}
