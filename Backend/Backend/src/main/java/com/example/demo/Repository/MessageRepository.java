package com.example.demo.Repository;

import com.example.demo.Models.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository< Message, Long> {
	
	@Query("SELECT m FROM Message m join m.chat c WHERE c.id = :chatId")
	public List<Message> findByChatId( @Param("chatId") Long chatId);
}
