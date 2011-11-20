package ru.ifmo.cis.mrp.back.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.back.ejb.SequenceOptimizer;
import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.entity.OrderContent;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Igor
 * Date: 16.11.11
 * Time: 16:40
 */
@Stateless
public class SequenceOptimizerImpl implements SequenceOptimizer {

    private LinkedList<Good> sequence;

    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceOptimizerImpl.class);

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;

    private void getBaseGoodsSequence() {
        LOGGER.info("[Back] Getting base goods sequence.");
        List<OrderContent> orderContentList = em.createQuery("from OrderContent ").getResultList();
        sequence = new LinkedList<Good>();
        for (OrderContent orderContent : orderContentList) {
            for (long i = 0; i < orderContent.getCount(); ++i) {
                sequence.add(orderContent.getGood());
            }
        }
        LOGGER.info("[Back] Goods for optimization: " + sequence.size());
    }

    @Override
    public LinkedList<Good> getSequence() {
        getBaseGoodsSequence(); //Fills sequence list from DB without any optimization
        //TODO: Implement sequence optimization!
        return sequence;
    }
}
