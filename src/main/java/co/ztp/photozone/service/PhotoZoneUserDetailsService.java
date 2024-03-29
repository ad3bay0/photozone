/**
 * 
 */
package co.ztp.photozone.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.ztp.photozone.model.User;
import co.ztp.photozone.model.UserDTO;
import co.ztp.photozone.repo.UserRepo;

/**
 * Service class that fetches the user records from the database
 * @author Adebayo Adeniyan
 * Jul 3, 2019
 */
@Service
public class PhotoZoneUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user == null) {
			
			throw  new UsernameNotFoundException("No user found with username: "+username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}
	
	public User save(UserDTO user) {
		User newUser =  new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(newUser);
		
	}

}
