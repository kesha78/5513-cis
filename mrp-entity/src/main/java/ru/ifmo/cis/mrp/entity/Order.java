package ru.ifmo.cis.mrp.entity;

import java.util.Collection;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ORDERS")
public class Order implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Collection<OrderContent> orderContents;

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

