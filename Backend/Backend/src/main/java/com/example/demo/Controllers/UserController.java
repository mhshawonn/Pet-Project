package com.example.demo.Controllers;

import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
@RestController
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	public ResponseEntity< User > getAuthenticatedUser()  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User current_user = (User) authentication.getPrincipal();
		return ResponseEntity.ok().body(current_user);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity< List< User > > getAllUsers(){
		return ResponseEntity.ok().body(userService.allUsers());
	}
	
	
	@GetMapping("/get/user")
	public ResponseEntity<User> getUserByTokenHandler(
			@RequestHeader(name = "Authorization") String token) throws UserException {
		
		System.out.println("token : " + token);

//		if (token == null || !token.startsWith("Bearer ")) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		}

		System.out.println("token : " + token);
		User user = userService.findUserByToken(token);
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/search")
	public ResponseEntity< List<User> > searchUserHandler( @RequestParam("name") String name){
		
		System.out.println("query : " + name);
		
		List<User> users = userService.searchUser(name);
		
		System.out.println("users : " + users);
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	
}
