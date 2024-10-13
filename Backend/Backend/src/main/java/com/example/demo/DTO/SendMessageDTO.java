package com.example.demo.DTO;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SendMessageDTO {
	private Long userId;
	private Long chatId;
	private String text;
	private String contentType;
	private String extension;
	private String fileUrl;
	private Long replyTo;   // message id
}
