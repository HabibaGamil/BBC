package com.springboot.firstapp.Service;

import java.util.List;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;

public interface AdminService {

	String addCategory(String name);
	
	String changeCategoryName(int id, String newName);
	
	String deleteCategory(int id);
	
	List<Category> getAllGategories();
	
	List<User> getAllUsers();
}
