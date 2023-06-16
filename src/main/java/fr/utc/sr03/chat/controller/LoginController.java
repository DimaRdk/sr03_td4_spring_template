package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        User userFromDB = userRepository.findByMail(user.getMail());

        if(userFromDB != null && userFromDB.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", userFromDB);
            return new ResponseEntity<>(userFromDB, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }
}