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

    /**
     *
     */
    private static final long serialVersionUID = -2744101220848041077L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Collection<OrderContent> orderContents;

    private int t;

    private int beginT;

    public int getBeginT() {
        return beginT;
    }

    public void setBeginT(int beginT) {
        this.beginT = beginT;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(Collection<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }
}

