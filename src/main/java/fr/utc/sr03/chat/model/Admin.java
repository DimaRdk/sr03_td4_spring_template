package fr.utc.sr03.chat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {


    public Admin() {

    }


    //TODO : implémenter createUser()
    public void createUser(){

    }
    //TODO : implémenter deleteUser()
    public void deleteUser(){

    }

    //TODO : implémenter getInactiveUsesr()
    public void getInactiveUsers(){

    }

    //TODO : implémenter deleteChat()
    public void deleteChat(){

    }
}
