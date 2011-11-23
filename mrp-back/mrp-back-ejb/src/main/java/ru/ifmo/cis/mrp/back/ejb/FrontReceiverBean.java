package ru.ifmo.cis.mrp.back.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.back.ejb.dao.OrderDao;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.entity.OrderContent;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.jms.*;

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

    private Long t;

    public FrontReceiverBean() {
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {       // ORDER
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                if (objectMessage.getObject() instanceof Order) {
                    Order receivedOrder = (Order) objectMessage.getObject();
                    LOGGER.info("[Back] Received Order");
                    //
                    long currentTick = 2;
                    for (OrderContent orderContent : receivedOrder.getOrderContents()) {
                        if ("good1".equals(orderContent.getGood().getName())) {
                            currentTick += Math.ceil(orderContent.getCount() / Production.x);
                        } else if (("good2".equals(orderContent.getGood().getName()))) {
                            currentTick += Math.ceil(orderContent.getCount() / Production.y);
                        } else if (("good3".equals(orderContent.getGood().getName()))) {
                            currentTick += Math.ceil(orderContent.getCount() / Production.z);
                        }
                    }
                    receivedOrder.setBeginT(t);
                    t += currentTick;
                    receivedOrder.setT(currentTick);
                    orderDao.create(receivedOrder);
                }
            } catch (JMSException e) {
                LOGGER.error("Exception while receiving message", e);
            }
        } else if (message instanceof TextMessage) {  //changeType request
            TextMessage txtMessage = (TextMessage) message;
            try {
                LOGGER.info("[Back] Received changeType request to " + txtMessage.getText());
            } catch (JMSException e) {
                LOGGER.error("Exception while getting changeType request", e);
            }
        }
    }
}
