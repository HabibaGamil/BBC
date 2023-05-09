package com.springboot.firstapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.firstapp.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query(value = "SELECT c FROM Category c WHERE c.name = ?1")
	Optional<Category> findByCategoryName(String name);
	
	@Query(value = "SELECT c FROM Category c ORDER BY id ASC")
	List<Category> getAllCategories();
	
}
