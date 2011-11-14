package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ORDER_CONTENT")
public class OrderContent implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Good good;

    @Column(nullable = false)
    private Long count;

}
