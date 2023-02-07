package com.nighthawk.spring_portfolio.mvc.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nighthawk.spring_portfolio.mvc.person.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Arrays;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private PersonJpaRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username); 
        Person person = repository.findByEmail(username);
		if (person != null && person.getRole().equals("admin")) {
			List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
					return new User(person.getEmail(), person.getPassword()
				, roles);
			
			
		} else if (person != null && person.getRole().equals("user")) {

			List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
			return new User(person.getEmail(), person.getPassword(), roles); 
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} 
	}
}