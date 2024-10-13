package com.example.demo.Controllers;


import com.example.demo.DTO.EditMessageDTO;
import com.example.demo.DTO.SendMessageDTO;
import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.MessageException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Message;
import com.example.demo.Models.User;
import com.example.demo.Services.MessageService;
import com.example.demo.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/message")
public class MessageController {
	
	private MessageService messageService;
	private UserService userService;
	
	public MessageController(MessageService messageService, UserService userService) {
		this.messageService = messageService;
		this.userService = userService;
	}
	
	
	public ResponseEntity<?> sendMessageHandler( @RequestBody SendMessageDTO req,
	                                            @RequestHeader("Authorization") String jwt )
			throws UserException, ChatException {
		
		User user = userService.findUserByToken(jwt);
		
		req.setUserId(user.getId());
		
		return new ResponseEntity<>(messageService.sendMessage(req), HttpStatus.OK);
		
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity< List< Message > > getChatsMessagesHandler( @PathVariable Long chatId,
	                                                                  @RequestHeader("Authorization") String jwt )
			throws ChatException, UserException {
		
		User user = userService.findUserByToken(jwt);
		List<Message> messages = messageService.getChatsMessages(chatId, user);
		
		return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity< ? > deleteMessageHandler( @PathVariable Long messageId,
	                                                           @RequestHeader("Authorization") String jwt )
			throws UserException, MessageException, MessageException {
		
		User user = userService.findUserByToken(jwt);
		messageService.deleteMessage(messageId, user);
		
		return new ResponseEntity<>("Message Deleted Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/read/{messageId}")
	public ResponseEntity<Message> hasReadMessageHandler(@PathVariable Long messageId,
	                                                     @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
		
		User user = userService.findUserByToken(jwt);
		Message message = messageService.hasReadMessage(messageId, user);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Message> EditMessageHandler(@PathVariable Long messageId,
													  @RequestBody EditMessageDTO editMessageDTO,
	                                                  @RequestHeader("Authorization") String jwt)
			throws UserException, MessageException {
		
		User user = userService.findUserByToken(jwt);
		Message message = messageService.EditMessage(messageId, user, editMessageDTO);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
