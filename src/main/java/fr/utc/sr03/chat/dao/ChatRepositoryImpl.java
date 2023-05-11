package fr.utc.sr03.chat.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

}
