package ru.ifmo.cis.mrp.front.ejb.impl;

import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.front.ejb.GoodsBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.11.11
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class GoodsBeanImpl implements GoodsBean {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;


    @Override
    public Collection<Good> getAllGoods() {
        return (Collection<Good>) em.createQuery("from Good").getResultList();
    }

    @Override
    public Good findGoodByName(String name) {
        return (Good) em.createQuery("from Good where name=:name").setParameter("name", name).getResultList().get(0);
    }
}

