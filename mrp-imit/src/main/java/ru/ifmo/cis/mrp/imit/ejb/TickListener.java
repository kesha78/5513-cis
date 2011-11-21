package ru.ifmo.cis.mrp.imit.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.Good;

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


    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                List<Good> goodSequence = (LinkedList<Good>) objectMessage.getObject();
                LOGGER.info("[Imit] Got goods sequence. Size is: " + goodSequence.size());
                if (objectMessage.propertyExists("supplyTime") && objectMessage.getBooleanProperty("supplyTime")) { //TODO:supplyRequestId vmesto supplyTime

                }
                //TODO: WORK WITH THAT SEQUENCE
            } catch (JMSException e) {
                LOGGER.error("Exception while receiving goods sequence.");
            }
        }
    }
}
