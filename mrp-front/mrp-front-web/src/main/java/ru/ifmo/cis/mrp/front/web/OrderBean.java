package ru.ifmo.cis.mrp.front.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.entity.OrderContent;
import ru.ifmo.cis.mrp.front.ejb.GoodsBean;
import ru.ifmo.cis.mrp.front.ejb.OrderSenderBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderBean.class);

    @EJB
    private OrderSenderBean orderSenderBean;

    @EJB
    private GoodsBean goodsBean;

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

    public void addGoodRequest(){
        if(order.getOrderContents()==null){
           order.setOrderContents(new ArrayList<OrderContent>());
        }
        OrderContent currentOrderContent= new OrderContent();
        currentOrderContent.setGood(new Good());
        order.getOrderContents().add(currentOrderContent);
    }

    public Collection<Good> getAllGoods(){
        LOGGER.info("ALL GOODS SIZE= " + goodsBean.getAllGoods().size());
        return goodsBean.getAllGoods();
    }
}
