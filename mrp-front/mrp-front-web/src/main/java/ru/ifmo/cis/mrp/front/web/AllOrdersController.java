package ru.ifmo.cis.mrp.front.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.Order;
import ru.ifmo.cis.mrp.front.ejb.OrdersBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Collection;

@ManagedBean
@ViewScoped
public class AllOrdersController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllOrdersController.class);

    @EJB
    private OrdersBean ordersBean;

    public Collection<Order> getAllOrders() {
        return ordersBean.getAllOrders();
    }

}
