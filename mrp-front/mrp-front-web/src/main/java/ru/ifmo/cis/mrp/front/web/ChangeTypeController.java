package ru.ifmo.cis.mrp.front.web;

import ru.ifmo.cis.mrp.front.ejb.OrderSenderBean;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 23.11.11
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ApplicationScoped
public class ChangeTypeController {

    @EJB
    private OrderSenderBean orderSenderBean;

    private String currentOrderStyle = "Manual";

    public void changeOrderStyle() {
        if ("Manual".equals(currentOrderStyle)) {
            currentOrderStyle = "Auto";
        } else {
            currentOrderStyle = "Manual";
        }
        orderSenderBean.sendOrderType(currentOrderStyle);
    }

    public String getCurrentOrderStyle() {
        return currentOrderStyle;
    }

    public void setCurrentOrderStyle(String currentOrderStyle) {
        this.currentOrderStyle = currentOrderStyle;
    }
}
