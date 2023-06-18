package fr.utc.sr03.chat.snippets;

import com.sun.tools.jconsole.JConsoleContext;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.exceptions.ChatNotFoundException;
import fr.utc.sr03.chat.exceptions.UserNotFoundException;
import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Constraint;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * URL du endpoint : http://localhost:8080/
 */
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

/**
 * Endpoint for API
 */
@RestController
@RequestMapping("api")
public class EndpointApiJson {

    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Chat repository
     */
    @Autowired
    private ChatRepository chatRepository;

    /**
     * Get user details
     * @param id user id
     * @return user details
     * @throws UserNotFoundException if user not found
     */
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

    /**
     * Send new password to user if the mail is known.
     * @param email user mail.
     * @return user password.
     * @throws UserNotFoundException if user not found
     */
    @GetMapping("forgotPassword")
    @ResponseBody // Pour faire sans template html
    public String getPasswordMail(@RequestParam String email) throws UserNotFoundException {
        User requestedUser= userRepository.findByMail(email);
        if (requestedUser != null ){
            String response = requestedUser.getPassword();
            return response;
        }
        else{
            throw new UserNotFoundException();
        }
    }

    /**
     * Try to log a user with given password and mail
     * @param mail user mail
     * @param password user password
     * @return User if logged
     * @throws UserNotFoundException if user not found
     */
    @GetMapping("login")
    @ResponseBody
    public User canLog(@RequestParam String mail, @RequestParam String password) throws UserNotFoundException {
        Optional<User> fetchedUser = Optional.ofNullable(userRepository.findByMail(mail));
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            if (user.getPassword().equals(password)) {
                if(user.getIsActive()){
                    return user;
                }else{

                    throw new UserNotFoundException();
                }

            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw  new UserNotFoundException();
        }
    }

    /**
     * Get chats created by a user
     * @param id user id
     * @return chats created by user
     */
    @GetMapping("createdChat")
    @ResponseBody // Pour faire sans template html
    public List<Chat> getUsersChat(@RequestParam Long id)
    {
        List<Chat> requestedChats = chatRepository.findByCreator_Id(id);
        return requestedChats;
    }

    /**
     * Get chats where a user is invited
     * @param id user id
     * @return chats where user is invited
     */
    @GetMapping("invitedChat")
    @ResponseBody
    public List<Chat> getUsersInvitation(@RequestParam Long id)
    {
        List<Chat> requestedChats= chatRepository.findByMembers_Id(id);
        return requestedChats;
    }

    /**
     * Delete a chat
     * @param id Chat id
     * @return 204 if deleted
     * @throws ChatNotFoundException
     */
    @PostMapping("Suprchat")
    @ResponseBody
    public ResponseEntity<String> deleteChat(@RequestParam String id) throws ChatNotFoundException {
        Long idTyped = Long.parseLong(id);
        Optional<Chat> requestedChat = chatRepository.findById(idTyped);
        System.out.println("on arrive ici");
        if (requestedChat.isPresent()) {
            Chat toDelete = requestedChat.get();
            chatRepository.delete(toDelete);
            return ResponseEntity.noContent().build();
        } else {
            throw new ChatNotFoundException();
        }
    }

    /**
     * Create a chat
     * @param creatorId chat creator id
     * @param title chat title
     * @param description chat description
     * @param creationDate chat creation date
     * @param expirationDate chat expiration date
     * @throws UserNotFoundException
     */
    @PostMapping("chat")
    @ResponseBody
    public void addNewChat( @RequestParam String creatorId,@RequestParam String title,@RequestParam String description , @RequestParam String creationDate ,@RequestParam String expirationDate ) throws UserNotFoundException {
        Long idTyped = Long.parseLong(creatorId);
        User creator = userRepository.findById(idTyped)
                .orElseThrow(() -> new UserNotFoundException());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime creationDateTyped = LocalDateTime.parse(creationDate, formatter);
        LocalDateTime expirationDateTyped = LocalDateTime.parse(expirationDate, formatter);
        Chat newChat = new Chat();
        newChat.setCreator(creator);
        newChat.setTitle(title);
        newChat.setDescription(description);
        newChat .setCreationDate(creationDateTyped);
        newChat.setExpirationDate(expirationDateTyped);

        chatRepository.save(newChat);

    }

    /**
     * Modify chat
     * @param toUpdate modified chat
     */
    @PutMapping("chat")
    @ResponseBody
    public void editChat(@RequestBody Chat toUpdate){
        chatRepository.save(toUpdate);
    }

    /**
     * Get chat by id
     * @param id chat id
     * @return chat
     */
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

    /**
     * Get chat members
     * @param id chat id
     * @return chat members
     */
    @GetMapping("chat/{id}/members")
    @ResponseBody
    public ResponseEntity<List<User>> getChatMembers(@PathVariable Long id) {
        Optional<Chat> optionalChat = chatRepository.findById(id);

        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            List<User> members = chat.getMembers();
            return ResponseEntity.ok(members);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * remove user from chat
     * @param chatId chat id
     * @param userId user id
     * @return 200 if removed
     */
    @DeleteMapping("/chat/{chatId}/members/{userId}")
    @ResponseBody
    public ResponseEntity<String> removeUserFromChat(@PathVariable Long chatId, @PathVariable Long userId) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);

        if(optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            List<User> members = chat.getMembers();

            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();

                if(members.contains(user)) {
                    members.remove(user);
                    chatRepository.save(chat);
                    return ResponseEntity.ok("User removed successfully from the chat");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in the chat");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat not found");
        }
    }

    /**
     * Update chat
     * @param id chat id
     * @param updatedChat chat with updated values
     * @return updated chat
     */
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

    /**
     * Get all active users
     * @return all active users
     */
    @GetMapping("users")
    @ResponseBody
    public ResponseEntity<List<User>> getActiveUsers() {
        // Assuming there's a method in your UserRepository called findUsersByIsActive
        List<User> activeUsers = userRepository.findByIsActive(true);

        return ResponseEntity.ok(activeUsers);
    }

    /**
     * Invite the given users in the given chat.
     * @param chatId Id of the chat you want to invite users to.
     * @param body Ids of the users to add.
     * @return HTTP Response code.
     */
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
