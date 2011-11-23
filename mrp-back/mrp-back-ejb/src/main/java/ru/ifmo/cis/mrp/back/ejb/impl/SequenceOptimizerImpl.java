package ru.ifmo.cis.mrp.back.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.back.ejb.SequenceOptimizer;
import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.entity.OrderContent;
import ru.ifmo.cis.mrp.entity.SupplyRequest;

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
        List<OrderContent> orderContentList = em.createQuery("from OrderContent ").getResultList();
        sequence = new LinkedList<Good>();
        for (OrderContent orderContent : orderContentList) {
            for (long i = 0; i < orderContent.getCount(); ++i) {
                sequence.add(orderContent.getGood());
            }
        }
    }

    @Override
    public LinkedList<Good> getSequence() {
        getBaseGoodsSequence(); //Fills sequence list from DB without any optimization
        //TODO: Implement sequence optimization!
        return sequence;
    }

    @Override
    public SupplyRequest countSupplyRequest(int estimate) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SupplyRequest countStatisticSupply(int estimate, SupplyRequest supplyRequest) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
