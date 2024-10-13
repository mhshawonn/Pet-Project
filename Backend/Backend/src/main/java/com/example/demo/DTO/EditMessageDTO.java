package com.example.demo.DTO;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditMessageDTO {
	
	private String text;
	private String contentType;
	private String extension;
	private String fileUrl;
}
