package ru.ifmo.cis.mrp.back.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.back.ejb.Production;
import ru.ifmo.cis.mrp.back.ejb.SequenceOptimizer;
import ru.ifmo.cis.mrp.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        getBaseGoodsSequence(); //Fills sequence list from DB without any optimization
        SupplyRequest supplyRequest = new SupplyRequest();
        Map<Material, Long> materialRequest = createEmptyMaterialMap();
        for (Good good : sequence.subList(0, estimate)) {
            try {
                List<MaterialsToGoods> materialsToGoodsList = em.createQuery("from MaterialsToGoods where good=:good").setParameter("good", good).getResultList();
                for (MaterialsToGoods materialsToGoods : materialsToGoodsList) {
                    if (materialsToGoods.getGood().getName().equals(good.getName())) {
                        for (Entry<Material, Long> entry : materialRequest.entrySet()) {
                            if (entry.getKey().getName().equals(materialsToGoods.getMaterial().getName())) {
                                if ("good1".equals(good.getName())) {
                                    entry.setValue(entry.getValue() + materialsToGoods.getCount() * Production.x);
                                } else if ("good2".equals(good.getName())) {
                                    entry.setValue(entry.getValue() + materialsToGoods.getCount() * Production.y);
                                } else if ("good3".equals(good.getName())) {
                                    entry.setValue(entry.getValue() + materialsToGoods.getCount() * Production.z);
                                }
                            }
                        }
                    }
                }

            } catch (NoResultException e) {
                LOGGER.error("[Back] No materials for " + good.getName() + "!");
            }
        }
        for (Entry<Material, Long> entry : materialRequest.entrySet()) {
            Supply supply = new Supply();
            supply.setMaterial(entry.getKey());
            supply.setCount(entry.getValue());
            supplyRequest.getSupplies().add(supply);
            LOGGER.info("[Back] SUPPLY: " + supply.getMaterial().getName() + " - " + supply.getCount());
        }
        return supplyRequest;
    }

    private Map<Material, Long> createEmptyMaterialMap() {
        Map<Material, Long> materialsToRequest = new HashMap<Material, Long>();
        List<Material> allMaterials = em.createQuery("from Material").getResultList();
        for (Material material : allMaterials) {
            materialsToRequest.put(material, (long) 0);
        }
        return materialsToRequest;
    }

    @Override
    public SupplyRequest countStatisticSupply(int estimate, SupplyRequest supplyRequest) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
