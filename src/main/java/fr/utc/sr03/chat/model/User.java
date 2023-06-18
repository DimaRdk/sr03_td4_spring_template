package fr.utc.sr03.chat.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity
 */
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

    @Column(name = "password", nullable = false)
    private String password;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> invitedChats = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Chat> createdChat;

    @Column(name = "admin")
    protected boolean admin;

    @Column(name = "isactive", nullable = false)
    private boolean isActive;

    public User() {
        isActive = true;
        admin = false;
    }

    public User(Long id, String firstName, String lastName,String mail , String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
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

    public long setId(Long newId) {
        return this.id = newId;
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
        if (invitedChats.size()>0){
            return invitedChats;
        }
        return null;
    }

    public void setInvitedChats(List<Chat> invitedChats) {
        this.invitedChats = invitedChats;
    }

    public void addInvitedChat(Chat newChat){
        if (this.invitedChats == null) {
            this.invitedChats = new ArrayList<>();
        }
        this.invitedChats.add(newChat);
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public List<Chat> getCreatedChat(){
        if (createdChat.size()>0){
            return createdChat;
        }
        return null;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
