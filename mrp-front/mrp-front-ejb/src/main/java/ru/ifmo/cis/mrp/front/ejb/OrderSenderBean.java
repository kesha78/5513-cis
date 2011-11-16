package ru.ifmo.cis.mrp.front.ejb;

import ru.ifmo.cis.mrp.entity.Order;

import javax.ejb.Local;


/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 14.11.11
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OrderSenderBean {

    boolean sendOrder(Order order);

}
