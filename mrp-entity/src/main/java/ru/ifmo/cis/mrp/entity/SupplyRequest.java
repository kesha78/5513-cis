package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SUPPLY_REQUEST")
public class SupplyRequest implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Supply supply; //TODO:Collection

    @Column
    private int date;

    @Column
    private int factDate;

    @Column
    private boolean today;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getFactDate() {
        return factDate;
    }

    public void setFactDate(int factDate) {
        this.factDate = factDate;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }
}
