package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.10.11
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MATERIALS")
public class Material {

    private Long id;
    private String name;
    private Long price;


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
