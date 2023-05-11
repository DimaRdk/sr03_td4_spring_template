package fr.utc.sr03.chat.controller;


// ...
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

/**
 * URL de base du endpoint : http://localhost:8080/login
 */
@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
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

            if (userAttempted.getPassword().length() < 8) {
                model.addAttribute("shortPassword", true);
                return "login";
            }

            if (userAttempted.getPassword().equals(user.getPassword())) {
                if (userAttempted.getAdmin()) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/home";
                }
            } else {
                attempts++;
                session.setAttribute("attempts", attempts);
                model.addAttribute("invalidPassword", true);
                return "login";
            }
        }
        model.addAttribute("invalidLogin", true);
        return "login";
    }}
