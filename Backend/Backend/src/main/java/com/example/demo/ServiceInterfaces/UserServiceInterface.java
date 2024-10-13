package com.example.demo.ServiceInterfaces;

import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.User;

import java.util.List;

public interface UserServiceInterface {
	public User findUserById( Long id) throws UserException;
	public User findUserByToken(String jwt) throws UserException;
	public List<User> searchUser( String query);
}
