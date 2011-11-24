package ru.ifmo.cis.mrp.back.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        List<GoodStorage> goodStorageList = em.createQuery("from GoodStorage ").getResultList();
        em.detach(goodStorageList);
        sequence = new LinkedList<Good>();
        for (OrderContent orderContent : orderContentList) {
            for (long i = 0; i < orderContent.getCount(); ++i) {
                boolean shouldAdd = true;
                if (!goodStorageList.isEmpty()) {
                    LOGGER.info("[Back] Checking the goodStorage to generate base goods sequence. Good storage size is" + goodStorageList.size());
                    for (GoodStorage goodStorage : goodStorageList) {
                        if (goodStorage.getGood().getName().equals(orderContent.getGood().getName())) {
                            if (goodStorage.getCount() > 0) {
                                LOGGER.info("[Back] Good " + goodStorage.getGood().getName() + " already on storage.");
                                shouldAdd = false;
                                em.detach(goodStorage);
                                goodStorage.setCount(goodStorage.getCount() - 1);
                            }
                        }
                    }
                }
                if (shouldAdd) {
                    sequence.add(orderContent.getGood());
                }
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
        LOGGER.info("[Back] Starting to count supply request");
        getBaseGoodsSequence(); //Fills sequence list from DB without any optimization
        SupplyRequest supplyRequest = new SupplyRequest();
        supplyRequest.setSupplies(new LinkedList<Supply>());
        Map<Material, Long> materialRequest = createEmptyMaterialMap();
        if (sequence.size() > estimate) {
            sequence = (LinkedList<Good>) sequence.subList(0, estimate);
        }
        for (Good good : sequence) {
            try {
                List<MaterialsToGoods> materialsToGoodsList = em.createQuery("from MaterialsToGoods where good=:good").setParameter("good", good).getResultList();
                for (MaterialsToGoods materialsToGoods : materialsToGoodsList) {
                    if (materialsToGoods.getGood().getName().equals(good.getName())) {
                        for (Entry<Material, Long> entry : materialRequest.entrySet()) {
                            if (entry.getKey().getName().equals(materialsToGoods.getMaterial().getName())) {
                                entry.setValue(entry.getValue() + materialsToGoods.getCount());

                            }
                        }
                    }
                }

            } catch (NoResultException e) {
                LOGGER.error("[Back] No materials for " + good.getName() + "!");
            }
        }
        List<MaterialStorage> materialStorageList = em.createQuery("from MaterialStorage").getResultList();

        if (materialStorageList.size() == 0) {
            initMaterialStorage();
        }

        materialStorageList = em.createQuery("from MaterialStorage").getResultList();
        for (MaterialStorage materialStorage : materialStorageList) {
            for (Entry<Material, Long> entry : materialRequest.entrySet()) {
                if (entry.getKey().getName().equals(materialStorage.getMaterial().getName())) {
                    Supply supply = new Supply();
                    supply.setMaterial(entry.getKey());
                    long count = entry.getValue() - materialStorage.getCount(); // look at material storage for existence materials.
                    if (count < 0) count = 0;
                    supply.setCount(count);
                    supplyRequest.getSupplies().add(supply);
                    LOGGER.info("[Back] SUPPLY: " + supply.getMaterial().getName() + " - " + count);
                }
            }
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


    private void initMaterialStorage() {
        LOGGER.info("[Back] Setting up materialStorage for first check...");
        List<Material> materials = em.createQuery("from Material").getResultList();
        for (Material material : materials) {
            MaterialStorage materialStorage = new MaterialStorage();
            materialStorage.setMaterial(material);
            materialStorage.setCount((long) 0);
            em.persist(materialStorage);
        }
    }

    @Override
    public SupplyRequest countStatisticSupply(int estimate, SupplyRequest supplyRequest) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
