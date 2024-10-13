package com.example.demo.Controllers;

import com.example.demo.Exceptions.ChatException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Models.Chat;
import com.example.demo.Models.User;
import com.example.demo.Repository.ChatRepository;
import com.example.demo.Services.ChatService;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/chat")
@RestController
public class ChatController {
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/single")
	public ResponseEntity< Chat > createChatHandler( @RequestParam Long otherUserId,
	                                                 @RequestHeader("Authorization") String jwt)
			throws UserException {
		
		User reqUser = userService.findUserByToken(jwt);
		Chat chat = chatService.createChat(reqUser, otherUserId);
		
		System.out.println(chat);
		
		return ResponseEntity.ok().body(chat);
	}
	
	
	@GetMapping("/{chatId}")
	public ResponseEntity< Chat > findChatByIdHandler( @PathVariable Long chatId
			,@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		
		Chat chat = chatService.findChatById(chatId);
		return ResponseEntity.ok().body(chat);
	}
	
	@DeleteMapping("/{chatId}/delete")
	public ResponseEntity< ? > deleteChatHandler(
			@PathVariable Long chatId,
			@RequestHeader("Authorization") String jwt)
			throws UserException, ChatException {
		
		User reqUser = userService.findUserByToken(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		
		return ResponseEntity.ok().body("Chat Deleted Successfully");
	}
	
	
}
