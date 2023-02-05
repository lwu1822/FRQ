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
		if (person != null) {
					return new User(person.getEmail(), person.getPassword()
				, new ArrayList<>());
			
			
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} 
	}
}