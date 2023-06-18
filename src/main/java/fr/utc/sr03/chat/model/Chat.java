package fr.utc.sr03.chat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Chat entity
 */
@Entity
@Table(name = "sr03_chats")
public class Chat{

    /**
     * Chat id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Chat title
     */
    @Column(name = "title")
    private String title;

    /**
     * Chat description
     */
    @Column(name = "description")
    private String description;

    /**
     * Chat creation date
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Chat expiration date
     */
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    /**
     * Chat creator
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    /**
     * Chat members
     */
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members = new ArrayList<>();

    public Chat() {}

    public Chat(String title , String description , User creator, LocalDateTime creationDate, LocalDateTime expirationDate){
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.creator = creator;
    }

    public Long getId(){
        return this.id;
    }

    public void setCreationDate(LocalDateTime time){
        this.creationDate = time ;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void addMembers(User u){
        if(this.members!=null){
            this.members.add(u);
        }
        else{
            this.members = new ArrayList<User>();
            this.members.add(u);
        }

    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setCreator(User creator){this.creator = creator;}

    public List<User> getMembers() {
        return members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}