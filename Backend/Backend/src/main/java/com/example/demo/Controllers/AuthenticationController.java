package com.example.demo.Controllers;


import com.example.demo.DTO.LoginUserDTO;
import com.example.demo.DTO.RegisterUserDTO;
import com.example.demo.DTO.VerifyUserDTO;
import com.example.demo.Models.User;
import com.example.demo.Responses.LoginResponse;
import com.example.demo.Services.AuthenticationService;
import com.example.demo.Services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	
	private final JwtService jwtService;
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> register( @RequestBody RegisterUserDTO registerUserDTO){
		User registeredUser = authenticationService.signup(registerUserDTO);
		return ResponseEntity.ok().body(registeredUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity< LoginResponse > authenticate(@RequestBody LoginUserDTO loginUserDTO){
		User authenticatedUser = authenticationService.authenticate(loginUserDTO);
		String jwt = jwtService.generateToken(authenticatedUser);
		LoginResponse loginResponse = new LoginResponse(jwt, jwtService.getExpirationTime());
		return ResponseEntity.ok(loginResponse);
	}
	
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDTO verifyUserDTO){
		try {
			authenticationService.verifyUser(verifyUserDTO);
			return ResponseEntity.ok().body("User Verified Successfully");
		}catch (Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/resend")
	public ResponseEntity<?> resendVerificationCode( @RequestParam String email ){
		try {
			authenticationService.resendVerificationCode(email);
			return ResponseEntity.ok().body("Verification Code Sent Successfully");
		}catch (Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
