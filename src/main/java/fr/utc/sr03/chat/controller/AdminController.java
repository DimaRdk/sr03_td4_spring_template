package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String getDashboard(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("disabledUsers")
    public String getDisabledUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "disabledUsers";
    }

    @GetMapping("disableUser")
    public String disableUser(@RequestParam Long id) {
        Optional<User> toDisable = userRepository.findById(id);
        if (toDisable.isPresent()){
            User user = toDisable.get();
            user.setIsActive(false);
            userRepository.save(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("enableUser")
    public String enableUser(@RequestParam Long id) {
        Optional<User> toEnable = userRepository.findById(id);
        if (toEnable.isPresent()){
            User user = toEnable.get();
            user.setIsActive(true);
            userRepository.save(user);
        }
        return "redirect:/admin/disabledUsers";
    }

    @GetMapping("deleteUser")
    public String deleteUser(@RequestParam Long id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()){
            User user = toDelete.get();
            userRepository.delete(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("createUser")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("createUser")
    public String createUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la création d'un utilisateur");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/admin/createUser";
        }
        User testUniq = userRepository.findByMail(user.getMail());
        if (testUniq != null){
            redirectAttributes.addFlashAttribute("message", "Le mail sélectionné n'est pas valide");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/admin/createUser";
        }
        String password = generateRandomString();

        user.setPassword(password);

        userRepository.save(user);


        redirectAttributes.addFlashAttribute("message", "Création de l'utilisateur réussie");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin";
    }


    public String generateRandomString() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
