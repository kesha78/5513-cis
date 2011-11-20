package ru.ifmo.cis.mrp.front.ejb;

import ru.ifmo.cis.mrp.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    EntityManager em;

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
        String queryString =
                "SELECT i from User i WHERE i.login LIKE :pattern ";

        Query query = em.createQuery(queryString);
        query.setParameter("pattern", "%" + login + "%");

        User user = (User) query.getSingleResult();
        return user;
    }


}
