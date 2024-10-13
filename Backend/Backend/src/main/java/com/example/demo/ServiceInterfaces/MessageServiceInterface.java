package com.example.demo.ServiceInterfaces;

import com.example.demo.DTO.EditMessageDTO;
import com.example.demo.DTO.SendMessageDTO;
import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.MessageException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Message;
import com.example.demo.Models.User;

import java.util.List;

public interface MessageServiceInterface {
	public Message sendMessage( SendMessageDTO req) throws UserException, ChatException;

	public List< Message > getChatsMessages( Long chatId, User reqUser) throws ChatException, UserException;

	public Message findMessageById(Long messageId) throws MessageException;

	public Message deleteMessage(Long messageId, User reqUser) throws MessageException;
	
	public Message hasReadMessage(Long messageId, User reqUser) throws MessageException;
	
	public Message EditMessage( Long messageId, User reqUser, EditMessageDTO editedMessageDTO ) throws MessageException;
}
