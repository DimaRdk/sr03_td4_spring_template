package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import fr.utc.sr03.chat.dao.ChatRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getLogin(Model model) {

        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    private static final int MAX_ATTEMPTS = 5;

    @PostMapping
    public String postLogin(@ModelAttribute User user, Model model, HttpSession session) {
        User userAttempted = userRepository.findByMail(user.getMail());

        if (userAttempted != null) {
            Integer attempts = (Integer) session.getAttribute("attempts");
            if (attempts == null) {
                attempts = 0;
            }

            if (attempts >= MAX_ATTEMPTS) {
                model.addAttribute("blocked", true);
                return "login";
            }
            if (userAttempted.getPassword().equals(user.getPassword())) {
                if (userAttempted.getAdmin()) {
                    session.setAttribute("loggedInUser", user);
                    return "redirect:/admin";
                } else {
                    model.addAttribute("invalidLogin", true);
                    return "login";
                }
            } else {
                attempts++;
                session.setAttribute("attempts", attempts);
                model.addAttribute("invalidPassword", true);
                return "login";
            }

        }else{
            model.addAttribute("invalidLogin", true);
            return "login";
        }
    }
}