package com.example.demo.Repository;

import com.example.demo.Models.Chat;
import com.example.demo.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends CrudRepository< Chat, Long>{
	@Query("select c from Chat c join c.members u where u.id=:userId")
	public List<Chat> findChatByUserId( @Param("userId") Long userId);
	
	@Query("select c from Chat c where :user Member of c.members and :reqUser Member of c.members")
	public Chat findSingleChatByUserIds( @Param ("user") User user, @Param("reqUser")User reqUser );
}
