package ru.ifmo.cis.mrp.front.ejb.ru.ifmo.cis.mrp.front.ejb;

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

    public boolean sendOrder(Order order);

}
