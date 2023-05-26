package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String handleForgotPassword(@RequestParam("email") String email) {
        User user = userRepository.findByMail(email);

//        if (user != null) {
//            emailService.sendPasswordToUser(user.getMail(), user.getPassword());
//        }
        if (user != null && user.getMail()!=null && user.getPassword() != null){
            System.out.println("Envoi de mail : ");
            System.out.println("Email de l'utilisateur : " + user.getMail());
            System.out.println("Password de l'utilisateur : "+ user.getPassword());

        }else{
            System.out.println("Mail inconnu de la base de donnée ");
        }

        return "redirect:/login";
    }
}
