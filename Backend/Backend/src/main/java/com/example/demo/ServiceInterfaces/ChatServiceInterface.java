package com.example.demo.ServiceInterfaces;

import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Chat;
import com.example.demo.Models.User;

import java.util.List;
import java.util.Optional;

public interface ChatServiceInterface {
	
	public Chat createChat( User reqUser, Long userId2 ) throws UserException;
	public Chat findChatById(Long chatId) throws ChatException;
	
	public Chat findSingleChatByUserIds(User reqUser, User otherUser) throws ChatException;
	
	public List<Chat> findAllChatByUserId( Long userId) throws UserException;
	
	
	public void deleteChat(Long chatId, Long userId) throws ChatException,UserException;
}
