package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@Column(name = "content_type", nullable = false)
	private String contentType;
	
	@Column(name = "extension", nullable = true)
	private String extension;
	
	@Column(name = "file_url", nullable = true)
	private String fileUrl;
	
	@Column(name = "is_read", nullable = false)
	private boolean isRead;
	
	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;
	
	@Column(name = "is_edited", nullable = false)
	private boolean isEdited;
	
	@Column(name = "reply_to", nullable = true)
	private Long replyTo;   // message id
	
	
	@Column(name = "timestamp", nullable = false)
	@OrderBy("timestamp ASC")
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "message_by")
	private User messageBy;
	
	@ManyToOne
	@JoinColumn(name = "chat_id")
	private Chat chat;
}
