package com.springboot.firstapp.Repository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.springboot.firstapp.Model.CachedUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CachedUserRepository {

	private static final String HASH_KEY = "LoggedInUsersCache";
	
	private final RedisTemplate template;
	
	@CachePut(value = "LoggedInUsersCache", key = "#cachedUser.getUserEmail()")
	public CachedUser save(CachedUser cachedUser) {
		//CachedUser.getUserDetails()
		System.out.println("hiiiiiiiii");
		System.out.println(cachedUser);
		System.out.println("---");
		template.opsForHash().put(HASH_KEY, cachedUser.getUserEmail(), cachedUser);
		//System.out.println(this.findAll().contains(cachedUser));
		//System.out.println(this.findAll().toString());
		return cachedUser;
	}
	
	
	public List<CachedUser> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }
	
	@Cacheable(value = "LoggedInUsersCache", key = "#userEmail")
	public CachedUser findCachedUserByEmail (String userEmail){
        System.out.println("el user mesh henaaaaaaaaaa");
        return ((CachedUser) template.opsForHash().get(HASH_KEY, userEmail));
    }
	
	
	@CacheEvict(value = "LoggedInUsersCache", key = "#userEmail")
	 public String deleteCachedUser(String userEmail){
	        template.opsForHash().delete(HASH_KEY,userEmail);
	        return "Token deleted successfully";
	    }
	
}
