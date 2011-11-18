package ru.ifmo.cis.mrp.back.ejb.impl;

import ru.ifmo.cis.mrp.back.ejb.SequenceOptimizer;
import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.entity.OrderContent;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * User: Igor
 * Date: 16.11.11
 * Time: 16:40
 */
@Stateless
public class SequenceOptimizerImpl implements SequenceOptimizer {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;

    private Collection<OrderContent> getAllOrderContent() {
        return em.createQuery("from OrderContent").getResultList();
    }

    @Override
    public List<Good> getSequence() {
        return null;  //TODO: Implement that!
    }

    @Override
    public List<Good> getSequence(Collection<Good> newCollection) {
        return null;  //TODO: Implement that too!
    }
}
