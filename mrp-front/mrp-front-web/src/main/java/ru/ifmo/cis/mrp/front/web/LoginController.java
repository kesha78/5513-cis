package ru.ifmo.cis.mrp.front.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.User;
import ru.ifmo.cis.mrp.front.ejb.UserBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1796101007168126159L;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @EJB
    private UserBean userBean;

    private User user;

    public String login(String login) {
        User user = userBean.findUserByLogin(login);
        if (user != null) {
            return "order.xhtml";
        } else {
            return "login.xhtml";
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
