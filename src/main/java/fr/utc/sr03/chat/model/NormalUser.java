package fr.utc.sr03.chat.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Normal user entity
 */
@Entity
@Table(name = "normal_users")
public class NormalUser extends User {

    public NormalUser() {
        super();}
}