package ru.ifmo.cis.mrp.front.ejb.impl;

import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.front.ejb.OrdersBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
public class OrdersBeanImpl implements OrdersBean {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;

    @Override
    public Collection<Order> getAllOrders() {
        return em.createQuery("from Order").getResultList();
    }

}
