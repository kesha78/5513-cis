package ru.ifmo.cis.mrp.back.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.back.ejb.dao.OrderDao;
import ru.ifmo.cis.mrp.entity.Order;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 14.11.11
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
@TransactionManagement
@MessageDriven(name = "FrontReceiverEJB", activationConfig =
        {@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/frontBack")})
public class FrontReceiverBean implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontReceiverBean.class);

    @EJB
    OrderDao orderDao;

    public FrontReceiverBean() {
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                if (objectMessage.getObject() instanceof Order) {
                    Order receivedOrder = (Order) objectMessage.getObject();
                    LOGGER.info("[Back] Received Order");
                    orderDao.create(receivedOrder);
                }
            } catch (JMSException e) {
                LOGGER.error("Exception while receiving message", e);
            }
        }
    }
}
