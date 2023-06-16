package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.exceptions.UserNotFoundException;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;



    @GetMapping()
    public String getDashboard(HttpSession session,Model model) {

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("disabledUsers")
    public String getDisabledUsers(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");


        if (user == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "disabledUsers";
    }

    @GetMapping("disableUser")
    public String disableUser(HttpSession session , Model model,@RequestParam Long id) throws UserNotFoundException {
        User userLogged = (User) session.getAttribute("loggedInUser");


        if (userLogged == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        Optional<User> toDisable = userRepository.findById(id);
        if (toDisable.isPresent()){
            User user = toDisable.get();
            user.setIsActive(false);
            userRepository.save(user);
        }
        else{
            throw new UserNotFoundException();
        }
        return "redirect:/admin";
    }

    @GetMapping("enableUser")
    public String enableUser(HttpSession session, Model model,@RequestParam Long id) throws UserNotFoundException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");


        if (loggedInUser == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }

        Optional<User> toEnable = userRepository.findById(id);
        if (toEnable.isPresent()){
            User user = toEnable.get();
            user.setIsActive(true);
            userRepository.save(user);
        }
        else{
            throw new UserNotFoundException();
        }
        return "redirect:/admin/disabledUsers";
    }

    @GetMapping("deleteUser")
    public String deleteUser(HttpSession session,Model model,@RequestParam Long id) throws UserNotFoundException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");


        if (loggedInUser == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isPresent()){
            User user = toDelete.get();
            userRepository.delete(user);
        }
        else{
            throw new UserNotFoundException();
        }
        return "redirect:/admin";
    }

    @GetMapping("createUser")
    public String getLogin(HttpSession session,Model model) {
        User user = (User) session.getAttribute("loggedInUser");


        if (user == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("createUser")
    public String createUser(HttpSession session, Model model,@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User userlog = (User) session.getAttribute("loggedInUser");


        if (userlog == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
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

        User testPasswd;
        String password;
        do{
            password = generateRandomString();
            testPasswd = userRepository.findByPassword(password);
        }while(testPasswd != null);

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

    @GetMapping("editUser")
    public String getEditForm(HttpSession session,@RequestParam Long id, Model model) throws UserNotFoundException {
        User userlogged = (User) session.getAttribute("loggedInUser");


        if (userlogged == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        Optional<User> toEdit = userRepository.findById(id);
        if (toEdit.isPresent()){
            User user = toEdit.get();
            model.addAttribute("user", user);
            return "editUser";
        }
        else{
            throw new UserNotFoundException();
        }
    }

    @PostMapping("editUser")
    public String editUser(HttpSession session, Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) throws UserNotFoundException {

        User userLogged = (User) session.getAttribute("loggedInUser");


        if (userLogged == null) {
            model.addAttribute("error", "Veuillez vous connecter.");
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            System.out.println("Erreur lors de la modification d'un utilisateur");
            return "redirect:/admin";
        }
        Optional<User> toUpdate = userRepository.findById(user.getId());
        User toSave;
        if (toUpdate.isPresent()){
            toSave = toUpdate.get();
        }else{
            throw new UserNotFoundException();
        }
        // Passage des nouvelles valeurs
        toSave.setFirstName(user.getFirstName());
        toSave.setLastName(user.getLastName());
        toSave.setMail(user.getMail());
        if (!user.getPassword().isEmpty()){
            toSave.setPassword(user.getPassword());
        }

        // Effectuer des vérifications supplémentaires avant d'ajouter l'user à la base de données
        User testUniq = userRepository.findByMail(toSave.getMail());
        if (testUniq != null){
            if (testUniq.getId() != toSave.getId()){
                System.out.println("Le mail selectionné n'est pas valide");
                return "redirect:/admin";
            }
        }
        // Ajouter l'user à la base de données

        // Envoyer un e-mail de confirmation avec le mot de passe temporaire
        // TODO
        System.out.println("Modification de l'utilisateur réussie");

        userRepository.save(toSave);
        return "redirect:/admin";
    }
}
