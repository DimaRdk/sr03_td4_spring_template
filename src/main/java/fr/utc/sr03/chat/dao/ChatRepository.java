package fr.utc.sr03.chat.dao;

import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Chat repository to access chats in database
 */
public interface ChatRepository extends ChatRepositoryCustom , JpaRepository<Chat, Long>,CrudRepository <Chat,Long> {

    /**
     * Find chat by id
     * @param creatorId id of the chatCreator
     * @return chat
     */
    List<Chat> findByCreator_Id(Long creatorId);

    /**
     * Find chat by member
     * @param user_id id of a member
     * @return chat
     */
    List<Chat> findByMembers_Id(Long user_id);
}
