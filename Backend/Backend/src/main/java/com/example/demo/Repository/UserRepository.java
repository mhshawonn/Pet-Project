package com.example.demo.Repository;

import com.example.demo.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository< User, Long>{
	public Optional<User> findByEmail( String email );
	
	public Optional<User> findByVerificationCode( String VerificationCode);
	
	@Query("select u from User u where u.name Like :query%")
	public List< User> searchUser( String query );
	
	
	
}
