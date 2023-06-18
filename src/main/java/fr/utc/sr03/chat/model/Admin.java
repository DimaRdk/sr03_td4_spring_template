package fr.utc.sr03.chat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Admin entity
 */
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
        super();
    }
}
