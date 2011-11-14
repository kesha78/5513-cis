package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SUPPLY_REQUEST")
public class SupplyRequest implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Supply supply;

    @Column
    private Date date;

    @Column
    private Date factDate;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFactDate() {
        return factDate;
    }

    public void setFactDate(Date factDate) {
        this.factDate = factDate;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }
}
