package ru.ifmo.cis.mrp.front.ejb.ru.ifmo.cis.mrp.front.ejb.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.front.ejb.ru.ifmo.cis.mrp.front.ejb.OrderSenderBean;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;


/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 14.11.11
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "OrderSenderEJB")
public class OrderSenderBeanImpl implements OrderSenderBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSenderBeanImpl.class);

    @Resource(mappedName= "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName="queue/frontBack")
    private Queue frontBackQueue;

    public OrderSenderBeanImpl() {
    }

    public boolean sendOrder(Order order){
        QueueConnection con = null;
        QueueSession ses = null;
        QueueSender sender = null;
        try
        {
            QueueConnectionFactory qcf = (QueueConnectionFactory) connectionFactory;
            con = qcf.createQueueConnection();
            ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = ses.createSender(frontBackQueue);
            ObjectMessage msg = ses.createObjectMessage(order);
            sender.send(msg);
        } catch (JMSException e){
            LOGGER.error("Exception while sending order from front to back",e);
        }
        return true;
    }
}
