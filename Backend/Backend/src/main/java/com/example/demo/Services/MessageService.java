package com.example.demo.Services;

import com.example.demo.DTO.EditMessageDTO;
import com.example.demo.DTO.SendMessageDTO;
import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.MessageException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Chat;
import com.example.demo.Models.Message;
import com.example.demo.Models.User;
import com.example.demo.Repository.MessageRepository;
import com.example.demo.ServiceInterfaces.MessageServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements MessageServiceInterface {
	private MessageRepository messageRepository;
	private UserService userService;
	private ChatService chatService;
	
	public MessageService( MessageRepository messageRepository, UserService userService, ChatService chatService ) {
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.chatService = chatService;
	}
	
	@Override
	public Message sendMessage( SendMessageDTO req ) throws UserException, ChatException {
		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());
		
		Message message = new Message();
		message.setChat(chat);
		message.setMessageBy(user);
		message.setText(req.getText());
		message.setContentType(req.getContentType());
		message.setExtension(req.getExtension());
		message.setFileUrl(req.getFileUrl());
		message.setTimestamp(LocalDateTime.now());
		message.setReplyTo(req.getReplyTo());
		
		return messageRepository.save(message);
	}
	
	
	@Override
	public List< Message > getChatsMessages( Long chatId, User reqUser ) throws ChatException, UserException {
		
		Chat chat = chatService.findChatById(chatId);
		
		if(!chat.getMembers().contains(reqUser)){
			throw new UserException("User not in chat : " + chat.getChatId());
		}
		
		List<Message> messages = messageRepository.findByChatId(chat.getChatId());
		
		return messages;
	}
	
	@Override
	public Message findMessageById( Long messageId ) throws MessageException {
		
		Optional<Message> opt = messageRepository.findById(messageId);
		
		if ( opt.isPresent() ) {
			return opt.get();
		}
		
		throw new MessageException("Message not found with id : " + messageId);
	}
	
	@Override
	public Message deleteMessage( Long messageId, User reqUser ) throws MessageException {
		Message message = findMessageById(messageId);
		
		if ( message.getMessageBy().getId().equals(reqUser.getId()) ){
			message.setDeleted(true);
			messageRepository.save(message);
			return message;
		} else {
			throw new MessageException("You can't delete another users message.");
		}
	}
	
	@Override
	public Message hasReadMessage( Long messageId, User reqUser ) throws MessageException {
		Message message = findMessageById(messageId);
		
		if ( message.getMessageBy().getId().equals(reqUser.getId()) ){
			message.setRead(true);
			messageRepository.save(message);
			return message;
		} else {
			throw new MessageException("You can't read another users message.");
		}
	}
	
	@Override
	public Message EditMessage( Long messageId, User reqUser, EditMessageDTO editedMessageDTO ) throws MessageException {
		Message message = findMessageById(messageId);
		
		if ( message.getMessageBy().getId().equals(reqUser.getId()) ){
			
			message.setText(editedMessageDTO.getText());
			message.setContentType(editedMessageDTO.getContentType());
			message.setExtension(editedMessageDTO.getExtension());
			message.setFileUrl(editedMessageDTO.getFileUrl());
			
			messageRepository.save(message);
			return message;
		} else {
			throw new MessageException("You can't edit another users message.");
		}
	}
}
