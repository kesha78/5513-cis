package ru.ifmo.cis.mrp.imit.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.*;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

@TransactionManagement
@MessageDriven(name = "TickListenerEJB", activationConfig =
        {@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "/topic/tickTopic")})
public class TickListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickListener.class);

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    EntityManager em;

    List<Good> goodHistory; //TODO

    SupplyRequest currentSupplyRequest;

    int supplyCounter = -1;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                if (objectMessage.getObject() instanceof LinkedList) { //sequence
                    List<Good> goodSequence = (LinkedList<Good>) objectMessage.getObject();
                    LOGGER.info("[Imit] Got goods sequence. Size is: " + goodSequence.size());

                    checkSupplyCounter();

                } else if (objectMessage.getObject() instanceof SupplyRequest) {

                    startSupplyCounter((SupplyRequest) objectMessage.getObject());

                }

            } catch (JMSException e) {
                LOGGER.error("Exception while receiving message from TickTopic.");
            }
        }
    }

    private void startSupplyCounter(SupplyRequest supplyRequest) throws JMSException {
        if (supplyRequest != null) {
            if (supplyRequest.getSupplies() != null) {
                LOGGER.info("[Imit] Got supplyRequest");
                currentSupplyRequest = supplyRequest;
                supplyCounter = 0;
            } else {
                LOGGER.info("[Imit] Got supplyRequest, but it's empty :(");
            }
        }
    }

    private void checkSupplyCounter() {
        if (supplyCounter == 2) {
            receiveMaterials();
        }
        if (supplyCounter >= 0) {
            ++supplyCounter;
        }
    }

    private void receiveMaterials() {
        LOGGER.info("[Imit] It's time to receive supply");
        supplyCounter = -1;

        //TODO: IMITATION EXCEPTIONS SHOULD BE IMPLEMENTED HERE!

        for (Supply supply : currentSupplyRequest.getSupplies()) {
            List<MaterialStorage> materialStorageList = em.createQuery("from MaterialStorage").getResultList();
            if (materialStorageList.size() == 0) {
                initMaterialStorage();
                materialStorageList = em.createQuery("from MaterialStorage").getResultList();
            }
            for (MaterialStorage materialStorage : materialStorageList) {
                if (materialStorage.getMaterial().getName().equals(supply.getMaterial().getName())) {
                    LOGGER.info("[Imit] Adding " + supply.getCount() + " to " + materialStorage.getMaterial().getName());
                    materialStorage.setCount(materialStorage.getCount() + supply.getCount());
                    em.merge(materialStorage);
                }
            }

        }
    }

    private void initMaterialStorage() {
        LOGGER.info("[Imit] Setting up materialStorage for first incoming...");
        List<Material> materials = em.createQuery("from Material").getResultList();
        for (Material material : materials) {
            MaterialStorage materialStorage = new MaterialStorage();
            materialStorage.setMaterial(material);
            materialStorage.setCount((long) 0);
            em.persist(materialStorage);
        }
    }
}
