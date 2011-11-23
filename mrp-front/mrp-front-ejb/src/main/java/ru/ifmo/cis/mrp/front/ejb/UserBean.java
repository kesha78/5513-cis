package ru.ifmo.cis.mrp.front.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBean.class);

    public void createUser(User user) {
        em.persist(user);
    }

    public void deleteUser(User user) {
        em.remove(user);
    }

    public void updateUser(User user) {
        em.refresh(user);
    }

    public User findUserById(Long id) {
        return em.find(User.class, id);
    }

    public User findUserByLogin(String login) {
        try {
            Object result = em.createQuery("from User where login=:login").setParameter("login", login).getSingleResult();
            return (User) result;
        } catch (NoResultException e) {
            LOGGER.error("Unknown user " + login + " try to login");
        }
        return null;
    }


}
