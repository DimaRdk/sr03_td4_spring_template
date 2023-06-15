package fr.utc.sr03.chat.snippets;

import com.sun.tools.jconsole.JConsoleContext;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.exceptions.ChatNotFoundException;
import fr.utc.sr03.chat.exceptions.UserNotFoundException;
import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
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
    public void deleteChat(@RequestParam Long id) throws ChatNotFoundException {
        Optional<Chat> requestedChat = chatRepository.findById(id);
        System.out.println("on arrive ici");
        if (requestedChat.isPresent()){
            Chat toDelete = requestedChat.get();
            chatRepository.delete(toDelete);
        }
        else{
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


    @PostMapping("login")
    @ResponseBody
    public User canLog(@RequestBody User requestedUser) throws UserNotFoundException {
        Optional<User> fetchedUser = Optional.ofNullable(userRepository.findByMail(requestedUser.getMail()));
        if (fetchedUser.isPresent()) {
            User user = fetchedUser.get();
            if (user.getPassword().equals(requestedUser.getPassword())) {
                return user;
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw  new UserNotFoundException();
        }
    }
}
