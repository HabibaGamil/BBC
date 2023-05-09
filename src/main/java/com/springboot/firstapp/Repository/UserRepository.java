package com.springboot.firstapp.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.springboot.firstapp.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	 @Procedure(name = "register")
	 int register(String username, String userpassword, String useremail, String usercountry, LocalDate user_date_of_birth);
	 
	 @Query(value = "SELECT s FROM User s WHERE s.email = ?1")
	 Optional<User> findByEmail(String email);
	 
	 @Procedure(name = "UpdatePassword")
	 void updatePassword(Long userid, String new_password);
	 
	 @Query(value = "SELECT s FROM User s")
	 List<User> getAllUsers();
	 
	 
}
