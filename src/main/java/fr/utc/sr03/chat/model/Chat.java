package fr.utc.sr03.chat.model;

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

@Entity
@Table(name = "sr03_chats")
public class Chat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "URL")
    private String link;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalTime creationDate;

    @Column(name = "expiration_date")
    private LocalTime expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    public Chat() {

    }

    public Chat(String Url , String title , String description , User creator){
        this.link= Url;
        this.title = title;
        this.description = description;
        this.creationDate = LocalTime.now();
        this.expirationDate = LocalTime.now().plusHours(48);
        this.creator = creator;

    }

    public Long getId(){
        return this.id;
    }

    public String getLink() {
        return link;
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
    public LocalTime getCreationDate() {
        return creationDate;
    }

    public LocalTime getExpirationDate() {
        return expirationDate;
    }

    public User getCreator() {
        return creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void inviteUser(User u){
        //TODO : ajouter
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