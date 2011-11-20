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
 *
 */
@ManagedBean
@ViewScoped
public class OrderController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

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

    public void sendOrder() {
        for (OrderContent orderContent : order.getOrderContents()) {
            if (orderContent.getGood().getId() == null) {
                LOGGER.info("Null good ID,fixing...");
                orderContent.setGood(goodsBean.findGoodByName(orderContent.getGood().getName()));
            }
        }
        orderSenderBean.sendOrder(order);
    }

    public void addGoodRequest() {
        if (order.getOrderContents() == null) {
            order.setOrderContents(new ArrayList<OrderContent>());
        }
        OrderContent currentOrderContent = new OrderContent();
        currentOrderContent.setGood(new Good());
        order.getOrderContents().add(currentOrderContent);
    }

    public Collection<Good> getAllGoods() {
        return goodsBean.getAllGoods();
    }
}
