package com.springboot.firstapp.Model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "LoggedInUsersCache")
public class CachedUser implements Serializable{

	@Id
	private String userEmail;
	
	private UserDetails userDetails;
	
	private String accessToken;
	
	private String refreshToken;
}
