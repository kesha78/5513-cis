package ru.ifmo.cis.mrp.front.ejb;

import ru.ifmo.cis.mrp.entity.Order;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface OrdersBean {

    Collection<Order> getAllOrders();

}
