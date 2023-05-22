package fr.utc.sr03.chat.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sr03_users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Merci de fournir un email valide")
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    //@NotBlank(message = "L'email est obligatoire")
    //@Size(min=8, message = "Le mot de passe doit comporter au moins 8 caractères")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Chat> invitedChats = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chat> createdChat = new ArrayList<>();

    @Column(name = "admin")
    protected boolean admin;

    @Column(name = "isactive", nullable = false)
    private boolean isActive;

    public User() {
        isActive = true;
        admin = false;
    }

    public User(String firstName, String lastName,String mail , String password) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.mail = mail;
    this.password = password;
        isActive = true;
        admin = false;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chat> getInvitedChats() {
        return invitedChats;
    }

    public void setInvitedChats(List<Chat> invitedChats) {
        this.invitedChats = invitedChats;
    }

    public void addInvitedChat(Chat newChat){
        this.invitedChats.add(newChat);
    }


    public boolean getAdmin() {
        return admin;
    }

    public List<Chat> getCreatedChat() {
        return createdChat;
    }

    public void setCreatedChat(List<Chat> createdChat) {
        this.createdChat = createdChat;
    }

    public void addCreatedChat(Chat newChat){
        this.createdChat.add(newChat);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    //TODO : implémenter createChat()
    public void createChat(){

    }
    //TODO : implémenter modifyChat()
    public void modifyChat(){

    }
    //TODO : implémenter deleteChat()
    public void deleteChat(){

    }
    //TODO : implémenter joinChat()
    public void joinChat(){

    }
    //TODO : implémenter sendMessage()
    public void sendMessage(){

    }


}
