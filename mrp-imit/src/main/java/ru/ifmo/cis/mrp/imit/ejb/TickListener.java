package ru.ifmo.cis.mrp.imit.ejb;

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
import java.util.List;

@TransactionManagement
@MessageDriven(name = "FrontReceiverEJB", activationConfig =
        {@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "/topic/tickTopic")})
public class TickListener implements MessageListener {

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    EntityManager em;


    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                List<Good> goodSequence = (List<Good>) objectMessage.getObject();
                //TODO: WORK WITH THAT SEQUENCE
            } catch (JMSException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
