package com.example.demo.Services;

import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Chat;
import com.example.demo.Models.User;
import com.example.demo.Repository.ChatRepository;
import com.example.demo.ServiceInterfaces.ChatServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements ChatServiceInterface {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserService userService;
	
	
	public ChatService( ChatRepository chatRepository, UserService userService ) {
		this.chatRepository = chatRepository;
		this.userService = userService;
	}
	
	@Override
	public Chat createChat( User reqUser, Long userId2 ) throws UserException {
		User otherUser = userService.findUserById(userId2);
		Chat doesChatExist = chatRepository.findSingleChatByUserIds(otherUser,reqUser);
		
		if(doesChatExist != null){
			return doesChatExist;
		}
		
		Chat chat = new Chat();
		chat.getMembers().add(reqUser);
		chat.getMembers().add(otherUser);
		
		chat = chatRepository.save(chat);
		
		
		return chat;
	}
	
	@Override
	public Chat findChatById( Long chatId ) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);
		
		if(chat.isPresent()){
			return chat.get();
			
		}
		
		throw new ChatException("Chat not found with id : " + chatId);
	}
	
	@Override
	public Chat  findSingleChatByUserIds( User reqUser, User otherUser ) throws ChatException {
		Chat chat = chatRepository.findSingleChatByUserIds(reqUser, otherUser);
		
		if ( chat == null ) {
			throw new ChatException("Chat not found with users : " + reqUser.getId() + " and " + otherUser.getId());
		}
		
		return chat;
	}
	
	@Override
	public List< Chat > findAllChatByUserId( Long userId ) throws UserException {
		User user = userService.findUserById(userId);
		
		if ( user == null ) {
			throw new UserException("User not found with id : " + userId);
		}
		
		List<Chat> chats = chatRepository.findChatByUserId(user.getId());
		
		return chats;
	}
	
	
	
	@Override
	public void deleteChat( Long chatId, Long userId ) throws ChatException, UserException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		
		if(opt.isPresent()){
			Chat chat = opt.get();
			
			if(!chat.getMembers().stream().anyMatch(user -> user.getId().equals(userId))){
				throw new UserException("User not in chat : " + chatId);
			}
			
			chatRepository.deleteById(chat.getChatId());
		}else{
			throw new ChatException("Chat not found with id : " + chatId);
		}
	}
}
