package com.springboot.firstapp.Service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl  implements AdminService{

	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	
	@Override
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

	
	@Override
	@Transactional
	public String changeCategoryName(int id,String newName) {

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
		return "updated successfully";
	}


	@Override
	@Transactional
	public String deleteCategory(int id) {
		
		Category category = categoryRepository.findById(id).orElse(null);
		if(category == null)
			return "this id is not found";
		
		List<User> users = userRepository.findAll();
		for(User user : users) {
			user.getPreferences().remove(category);
			userRepository.save(user);
		}
		
		categoryRepository.delete(category);
		return "deleted successfully";
	}
	
	
	public List<Category> getAllGategories() {
		return categoryRepository.getAllCategories();
	}

	@Override
	public List<User> getAllUsers(){
		return userRepository.getAllUsers();
	}

}
