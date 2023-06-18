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

/**
 * Controller to handle login page
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * User repository to access users in database
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Show login page
     * @param model model to add attributes used in view
     * @return login page
     */
    @GetMapping
    public String getLogin(Model model) {

        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * Show forgot password page
     * @return forgot password page
     */
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    private static final int MAX_ATTEMPTS = 5;

    /**
     * Handle login form
     * @param user user to login
     * @param model model to add attributes used in view
     * @param session session to store user
     * @return login page if login failed, admin page if login succeeded
     */
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