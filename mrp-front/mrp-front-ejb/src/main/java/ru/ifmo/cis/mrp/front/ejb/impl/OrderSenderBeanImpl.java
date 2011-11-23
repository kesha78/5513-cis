package ru.ifmo.cis.mrp.front.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.front.ejb.OrderSenderBean;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;


/**
 * User: Igor
 * Date: 14.11.11
 * Time: 18:25
 */
@Stateless(name = "OrderSenderEJB")
public class OrderSenderBeanImpl implements OrderSenderBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSenderBeanImpl.class);

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "queue/frontBack")
    private Queue frontBackQueue;

    public OrderSenderBeanImpl() {
    }

    public void sendOrder(Order order) {
        LOGGER.info("[Front] Sending order");
        QueueConnection con = null;
        QueueSession ses = null;
        QueueSender sender = null;
        try {
            QueueConnectionFactory qcf = (QueueConnectionFactory) connectionFactory;
            con = qcf.createQueueConnection();
            ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = ses.createSender(frontBackQueue);
            ObjectMessage msg = ses.createObjectMessage(order);
            sender.send(msg);
        } catch (JMSException e) {
            LOGGER.error("Exception while sending order from front to back", e);
        } finally {
            try {
                if (sender != null)
                    sender.close();
                if (ses != null)
                    ses.close();
                if (con != null)
                    con.close();
            } catch (JMSException e) {
                LOGGER.error("Exception while closing JMS session", e);
            }

        }

    }

    @Override
    public void sendOrderType(String type) {
        LOGGER.info("[Front] Sending changeType request");
        QueueConnection con = null;
        QueueSession ses = null;
        QueueSender sender = null;
        try {
            QueueConnectionFactory qcf = (QueueConnectionFactory) connectionFactory;
            con = qcf.createQueueConnection();
            ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = ses.createSender(frontBackQueue);
            TextMessage msg = ses.createTextMessage(type);
            sender.send(msg);
        } catch (JMSException e) {
            LOGGER.error("Exception while sending changeType request from front to back", e);
        } finally {
            try {
                if (sender != null)
                    sender.close();
                if (ses != null)
                    ses.close();
                if (con != null)
                    con.close();
            } catch (JMSException e) {
                LOGGER.error("Exception while closing JMS session", e);
            }

        }
    }
}
