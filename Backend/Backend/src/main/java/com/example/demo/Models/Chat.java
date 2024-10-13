package com.example.demo.Models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chats")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chatId;
	
	//later we can add chat name and image -> when we implement group chat
//	@Column(name = "chat_name", nullable = false)
//	private String chatName;
//
//	@Column(name = "chat_image", nullable = false)
//	private String chatImage;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "chat_users",
			joinColumns = @JoinColumn(name = "chat_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> members = new HashSet<User>(); // for now only 2 members are allowed-> 1-1 chat
	
	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
	private List<Message> messages = new ArrayList<Message>();
}
