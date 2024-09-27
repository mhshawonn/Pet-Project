package com.example.demo.Repository;

import com.example.demo.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository< User, Long>{
	Optional<User> findByEmail( String email );
	
	Optional<User> findByVerificationCode( String VerificationCode);
	
	
}
