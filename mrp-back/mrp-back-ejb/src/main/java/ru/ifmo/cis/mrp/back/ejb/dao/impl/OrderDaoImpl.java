package ru.ifmo.cis.mrp.back.ejb.dao.impl;

import ru.ifmo.cis.mrp.back.ejb.dao.OrderDao;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.entity.OrderContent;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: Igor
 * Date: 20.11.11
 * Time: 13:21
 */
@Stateless
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;

    @Override
    public Order create(Order order) {
        for (OrderContent orderContent : order.getOrderContents()) {
            em.persist(orderContent);
        }
        return em.merge(order);
    }
}
