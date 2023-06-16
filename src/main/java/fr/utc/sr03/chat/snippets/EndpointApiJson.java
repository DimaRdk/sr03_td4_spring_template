package fr.utc.sr03.chat.snippets;

import com.sun.tools.jconsole.JConsoleContext;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.exceptions.ChatNotFoundException;
import fr.utc.sr03.chat.exceptions.UserNotFoundException;
import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Constraint;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * URL du endpoint : http://localhost:8080/
 */
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RestController
@RequestMapping("api")
public class EndpointApiJson {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("userDetails")
    @ResponseBody // Pour faire sans template html
    public User getUserDetails(@RequestParam Long id) throws UserNotFoundException {
        Optional<User> requestedUser= userRepository.findById(id);
        if (requestedUser.isPresent()){
            User response = requestedUser.get();
            return response;
        }
        else{
            throw new UserNotFoundException();
        }
    }
    @GetMapping("login")
    @ResponseBody
    public User canLog(@RequestParam String mail, @RequestParam String password) throws UserNotFoundException {
        Optional<User> fetchedUser = Optional.ofNullable(userRepository.findByMail(mail));
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw  new UserNotFoundException();
        }
    }

    @GetMapping("createdChat")
    @ResponseBody // Pour faire sans template html
    public List<Chat> getUsersChat(@RequestParam Long id)
    {
        List<Chat> requestedChats = chatRepository.findByCreator_Id(id);
        return requestedChats;
    }

    @GetMapping("invitedChat")
    @ResponseBody
    public List<Chat> getUsersInvitation(@RequestParam Long id)
    {
        List<Chat> requestedChats= chatRepository.findByMembers_Id(id);
        return requestedChats;
    }

    @DeleteMapping("chat")
    @ResponseBody
    public ResponseEntity<String> deleteChat(@RequestParam Long id) throws ChatNotFoundException {
        Optional<Chat> requestedChat = chatRepository.findById(id);
        System.out.println("on arrive ici");
        if (requestedChat.isPresent()) {
            Chat toDelete = requestedChat.get();
            chatRepository.delete(toDelete);
            return ResponseEntity.noContent().build();
        } else {
            throw new ChatNotFoundException();
        }
    }

    @PostMapping("chat")
    @ResponseBody
    public void addNewChat(@RequestBody Chat toCreate, @RequestParam Long creatorId) throws UserNotFoundException {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new UserNotFoundException());
        toCreate.setCreator(creator);
        chatRepository.save(toCreate);
    }

    @PutMapping("chat")
    @ResponseBody
    public void editChat(@RequestBody Chat toUpdate){
        //TODO verifier que le chat est OK + confirmation
        chatRepository.save(toUpdate);
    }
    @GetMapping("chat")
    @ResponseBody
    public ResponseEntity<Chat> getChatById(@RequestParam Long id)
    {
        Optional<Chat> optionalChat = chatRepository.findById(id);

        if(optionalChat.isPresent()){
            return ResponseEntity.ok(optionalChat.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("chat/{id}")
    @ResponseBody
    public ResponseEntity<Chat> updateChat(@PathVariable Long id, @RequestBody Chat updatedChat) {
        Optional<Chat> optionalChat = chatRepository.findById(id);

        if (optionalChat.isPresent()) {
            Chat existingChat = optionalChat.get();

            // Mise à jour des champs du chat existant avec les valeurs du chat mis à jour
            existingChat.setTitle(updatedChat.getTitle());
            existingChat.setDescription(updatedChat.getDescription());

            existingChat.setExpirationDate(updatedChat.getExpirationDate());

            // Sauvegarde du chat mis à jour dans la base de données
            chatRepository.save(existingChat);

            // Retourne le chat mis à jour avec un statut HTTP 200 (OK)
            return ResponseEntity.ok(existingChat);
        } else {
            // Si aucun chat n'est trouvé avec l'ID donné, retourne un statut HTTP 404 (Non trouvé)
            return ResponseEntity.notFound().build();
        }
    }


        @GetMapping("users")
        @ResponseBody
        public ResponseEntity<List<User>> getActiveUsers() {
            // Assuming there's a method in your UserRepository called findUsersByIsActive
            List<User> activeUsers = userRepository.findByIsActive(true);

            return ResponseEntity.ok(activeUsers);
        }


    @PostMapping("/{chatId}/invite")
    @ResponseBody
    public ResponseEntity<?> inviteUser(@PathVariable Long chatId, @RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID must be provided");
        }

        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.addInvitedChat(chat);

        userRepository.save(user);

        return ResponseEntity.ok().body("User invited successfully");
    }



}
