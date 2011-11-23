package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Collection<OrderContent> orderContents;

    private Boolean important;

    private Long t;

    private Long beginT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }

    public Long getBeginT() {
        return beginT;
    }

    public void setBeginT(Long beginT) {
        this.beginT = beginT;
    }

    public Collection<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(Collection<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }
}

