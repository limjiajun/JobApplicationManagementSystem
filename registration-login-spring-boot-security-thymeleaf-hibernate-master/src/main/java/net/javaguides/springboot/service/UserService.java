package net.javaguides.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.web.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService{


	User save(UserRegistrationDto registrationDto);
	Optional<User> findByEmail(String email);
	List<User> getAllUsers(); // Fetch all users
	void deleteUser(Long id); // Delete a user by ID

}
