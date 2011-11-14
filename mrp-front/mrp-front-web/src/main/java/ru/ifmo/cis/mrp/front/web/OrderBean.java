package ru.ifmo.cis.mrp.front.web;

import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.front.ejb.ru.ifmo.cis.mrp.front.ejb.OrderSenderBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class OrderBean implements Serializable{

    @EJB
    private OrderSenderBean orderSenderBean;

    private Order order = new Order();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void sendOrder(){
        orderSenderBean.sendOrder(order);
    }
}
