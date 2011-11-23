package ru.ifmo.cis.mrp.front.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifmo.cis.mrp.entity.User;
import ru.ifmo.cis.mrp.front.ejb.UserBean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @EJB
    private UserBean userBean;

    private User user;

    private String loginString;

    public String doLogin() {
        LOGGER.info("Trying to login...");
        user = userBean.findUserByLogin(loginString);
        if (user != null) {
            return "order.xhtml";
        } else {
            FacesContext.getCurrentInstance().addMessage("login", new FacesMessage("Error"));
            return null;
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setLoginString(String loginString) {
        this.loginString = loginString;
    }


    public String getLoginString() {
        return loginString;
    }


}
