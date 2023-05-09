package com.springboot.firstapp.Config;

import java.io.IOException;
import java.util.Optional;

import org.hibernate.annotations.Comment;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter{

	private final JwtService jwtService;
	private final  UserDetailsService userDetailsService;
	private final UserRepository userRepository;
	private final CachedUserRepository cachedUserRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		final String urlString = request.getRequestURI().substring(request.getContextPath().length());
		
		final String jwt;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		System.out.println("filterrrrrrrrrrrrrrrrrrrrrr");
		
		//extract the jwt token
		jwt = authHeader.substring(7);
		//extract the user email from that token 
		userEmail = jwtService.extractUserName(jwt);
		if(jwtService.isTokenExpired(jwt))
			System.out.println("expireddddddddddddd");
		
		//if the token contains a user email
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//here we compare between the time to fetch user from database and cache
			//the time using cache is less than one third time using database
			
			/*
			long startTime1 = System.nanoTime();
			UserDetails userDetails2 = userDetailsService.loadUserByUsername(userEmail);
			long elapsedTime = System.nanoTime() - startTime1;
			System.out.println("database calling takes : "+ elapsedTime);
			System.out.println("************");
			*/
			
			long startTime2 = System.nanoTime();
			CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(userEmail);
			long elapsedTime2 = System.nanoTime() - startTime2;
			System.out.println("cache calling takes : "+ elapsedTime2);
			System.out.println(cachedUser);
			
			//if this token is not the cache, then the user need to login again
			if(cachedUser == null) {
				filterChain.doFilter(request, response);
				System.out.println("This token represents email that does not exist in the cache, you may need to login");
				return;
			}
			
			//get the userDetails object from the cache
			UserDetails userDetails = cachedUser.getUserDetails();
			
			/****************only admin*********************/
			if(userDetails != null && !(((User) userDetails).isAdmin()) && urlString.contains("admin")) {
				filterChain.doFilter(request, response);
				System.out.println("only admin");
				return;
			}
			/**************************************/
			/*************************added section***************************************/
			if(request.getParameter("user_id") != null) {
				final Long id = Long.parseLong(request.getParameter("user_id"));
				if(id != ((User) userDetails).getId()) {
					System.out.println("you are not authorized");
					filterChain.doFilter(request, response);
					return;
				}
			}
			/**************************************************************************/	
			
			//get the token from the cache
			String cachedToken = cachedUser.getAccessToken();
			
			//We have to check that the token is a valid token and not expired, 
			//and also the given token is equivalent to the token found in the cache
			if(jwtService.isValidToken(jwt, userDetails) && cachedToken != null && 
					cachedToken.equals(jwt)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, 
						null, 
						userDetails.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
			//In that case, the token is invalid, so you have to login again
			else
				System.out.println("token is invalid");
		}

		filterChain.doFilter(request, response);
	}

}
