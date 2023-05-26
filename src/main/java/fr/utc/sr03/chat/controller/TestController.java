package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.model.Chat;
import fr.utc.sr03.chat.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * URL du endpoint : http://localhost:8080/test
 */
@Controller
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("/test")
    @ResponseBody // Pour faire sans template html
    public String test() {
        LOGGER.info("=== ALL USERS ===");
    	List<User> users = userRepository.findAll();
        users.forEach(user -> {
            LOGGER.info(user.getFirstName() + " : " + user.getAdmin());
        });
        //userRepository.save(new User("RUdenko","Dimitry","mail","pw"));
        LOGGER.info("=== ALL USERS ===");
        List<User> users2 = userRepository.findAll();
        users2.forEach(user -> {
            LOGGER.info(user.getFirstName() + " : " + user.getAdmin());
        });

        LOGGER.info("=== USERS BY NAME AND PASSWORD ===");
        User user1 = userRepository.findByMailAndPassword("jb@test.com", "jb");
        LOGGER.info("user1 = " + user1.getMail());
        User user2 = userRepository.findByMailAndPassword("pf@test.com", "pf");
        LOGGER.info("user2 = " + user2);

        LOGGER.info("=== NOMS >= 5 caracteres ===");
        List<User> usersCustomQuery = userRepository.findByLastNameLength(5);
        usersCustomQuery.forEach(admin -> {
            LOGGER.info(admin.getLastName());
        });

        LOGGER.info("=== ADMIN ONLY ===");
        List<User> admins = userRepository.findAdminOnly();
        admins.forEach(admin -> {
            LOGGER.info(admin.getFirstName() + " : " + admin.getAdmin());
        });
        User user = userRepository.findByMailAndPassword("jb@test.com", "jb");

        LOGGER.info("=== Chats dispo  ===");
        List<Chat> chats = chatRepository.findAll();

        chats.forEach(chat -> {
            LOGGER.info(" Nom du chat :"+chat.getTitle() + " | Description : " + chat.getDescription()+"| Createur : "+ chat.getCreator().getFirstName());
        });


        LOGGER.info("==== Chat d'un user  proprietaire=====");

        List<Chat> chatU = chatRepository.findByCreator_Id(user.getId());
        chatU.forEach(chat -> {
            LOGGER.info(" Nom du chat :"+chat.getTitle() + " | Description : " + chat.getDescription()+"| Createur : "+ chat.getCreator().getFirstName());
        });


        Chat chat1 = chatRepository.findByTitle("Chat 1");
        chat1.addMembers(user2);
        System.out.println(user2);
        chatRepository.save(chat1);
        LOGGER.info("==== Chat d'un user  membre=====");
        LOGGER.info(user2.getFirstName());
        List<Chat> chatM = chatRepository.findByMembers_Id(user2.getId());
        chatU.forEach(chat -> {
            LOGGER.info(" Nom du chat :"+chat.getTitle() + " | Description : " + chat.getDescription()+"| Createur : "+ chat.getCreator().getFirstName());
        });

        LOGGER.info("=== INSERT ===");
        User newUser = new User();
        newUser.setFirstName("Sophie");
        newUser.setLastName("REDONCULE");
        newUser.setMail("sr@test.com");
        newUser.setPassword("sr");

        LOGGER.info(newUser.getFirstName() + " " + newUser.getLastName() + " inserted");



        return "ok";
    }
}
