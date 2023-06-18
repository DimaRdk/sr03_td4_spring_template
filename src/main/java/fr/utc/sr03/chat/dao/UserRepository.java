package fr.utc.sr03.chat.dao;

import fr.utc.sr03.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User repository to access users in database
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * Find user by mail
     * @param mail mail of the user
     * @return user
     */
    User findByMail(@Param("mail") String mail);

    /**
     * Find user by password
     * @param password password of the user
     * @return user
     */
    User findByPassword(@Param("password") String password);

    /**
     * Find users by isActive
     * @param isActive isActive of the user
     * @return Active users
     */
    List<User> findByIsActive(@Param("isActive") boolean isActive);

}
