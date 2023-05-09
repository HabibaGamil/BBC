package com.springboot.firstapp.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping("/test")
	public String test() {
		return "admin end point";
	}
	
	@PostMapping("/add-category")
	public String addCategory(@RequestParam("name") String name) {
		return adminService.addCategory(name);
	}
	
	@PostMapping("/change-categoryName")
	public String changeCategoryName(@RequestParam(name = "category_id") int id,
								@RequestParam(name = "new_name") String newName) {
		return adminService.changeCategoryName(id, newName);
	}
	
	@PostMapping("/delete-category")
	public String deleteCatgeory(@RequestParam(name = "category_id") int id) {
		return adminService.deleteCategory(id);
	}
	
	@GetMapping("/get-categories")
	public List<Category> getAllCategories(){
		return adminService.getAllGategories();
	}
	
	@GetMapping("/get-users")
	public List<User> getAllUsers(){
		return adminService.getAllUsers();
	}
}
