package com.example.demo.Services;

import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceInterfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;
	
	private final JwtService jwtService;
	
	public UserService( UserRepository userRepository, JwtService jwtService ) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}
	
	public List< User > allUsers(){
		return (List< User >) userRepository.findAll();
	}
	
	@Override
	public User findUserById( Long id ) throws UserException {
		Optional<User> opt = userRepository.findById(id);
		
		if(opt.isPresent()){
			return opt.get();
		}
		
		throw new UserException("User not found with id "+ id);
	}
	
	@Override
	public User findUserByToken( String jwt ) throws UserException {
		String userEmail = jwtService.getEmailFromToken(jwt);
		
		if( userEmail == null ){
			throw new UserException("Invalid token");
		}
		
		Optional<User> user = userRepository.findByEmail(userEmail);
		
		if( user.isEmpty() ){
			throw new UserException("User not found with token "+ jwt);
		}
		
		return user.get();
	}
	
	@Override
	public List< User > searchUser( String query ) {
		List<User> users = userRepository.searchUser(query);
		return users;
	}
}
