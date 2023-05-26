package fr.utc.sr03.chat.snippets;

import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * URL du endpoint : http://localhost:8080/snippets/users/json
 */
@RestController
@RequestMapping("api")
public class EndpointApiJson {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("userDetails")
    @ResponseBody // Pour faire sans template html
    public User getUserDetails(@RequestParam Long id)
    {
        Optional<User> requestedUser= userRepository.findById(id);
        if (requestedUser.isPresent()){
            User response = requestedUser.get();
            return response;
        }
        return null;
    }

    @GetMapping("createdChat")
    @ResponseBody // Pour faire sans template html
    public List<Chat> getUsersChat(@RequestParam Long id)
    {
        List<Chat> requestedChats= chatRepository.findByCreator_Id(id);
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
    public void deleteChat(@RequestParam Long id) throws Exception {
        Optional<Chat> requestedChat = chatRepository.findById(id);
        if (requestedChat.isPresent()){
            Chat toDelete = requestedChat.get();
            chatRepository.delete(toDelete);
        }
        else{
            throw new Exception();
        }
    }

    @PostMapping("chat")
    public void addNewChat(@Valid @RequestParam Chat toCreate){
        //TODO verifier que le chat est OK
        chatRepository.save(toCreate);
    }

    @PutMapping("chat")
    public void editChat(@Valid @RequestParam Chat toUpdate){
        //TODO verifier que le chat est OK
        chatRepository.save(toUpdate);
    }
}
