package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

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

    @GetMapping("createUser")
    public String getLogin(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("createUser")
    public String createUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Erreur lors de la création d'un utilisateur");
            return "createUser";
        }
        System.out.println("Création de l'utilisateur réussie");

        // Effectuer des vérifications supplémentaires avant d'ajouter l'user à la base de données
        // TODO verif unicité mail
        User testUniq = userRepository.findByMail(user.getMail());
        if (testUniq != null){
            System.out.println("Le mail selectionné n'est pas valide");
            return "createUser";
        }
        String password = generateRandomString();

        user.setPassword(password);

        // Ajouter l'user à la base de données
        userRepository.save(user);
        // Envoyer un e-mail de confirmation avec le mot de passe temporaire
        // TODO
        return "confirmation";
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
