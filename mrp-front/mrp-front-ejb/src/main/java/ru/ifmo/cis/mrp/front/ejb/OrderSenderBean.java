package ru.ifmo.cis.mrp.front.ejb;

import ru.ifmo.cis.mrp.entity.Order;

import javax.ejb.Local;


/**
 * User: Igor
 * Date: 14.11.11
 * Time: 18:25
 */
@Local
public interface OrderSenderBean {

    void sendOrder(Order order);

}
