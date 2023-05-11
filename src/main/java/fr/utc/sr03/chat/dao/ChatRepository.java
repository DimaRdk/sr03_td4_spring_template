package fr.utc.sr03.chat.dao;

import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends ChatRepositoryCustom , JpaRepository<Chat, Long>,CrudRepository <Chat,Long> {

    Chat findByTitle(@Param("title") String title);
    List<Chat> findByCreator_Id(Long creatorId);

    List<Chat> findByMembers_Id(Long user_id);


    // Ajouter et mettre à jour un chat (ces opérations sont fournies par JpaRepository)
    // save(Chat chat) : pour ajouter un nouveau chat ou mettre à jour un chat existant
    // exemple d'utilisation :
    // Chat newChat = new Chat(...);
    // chatRepository.save(newChat);

    // Chat updatedChat = chatRepository.findById(chatId).orElseThrow(...);
    // updatedChat.setTitle(newTitle);
    // chatRepository.save(updatedChat);

    // Récupérer un chat par son ID
}
