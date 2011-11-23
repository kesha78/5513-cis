package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 23.11.11
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ILLIQUID_GOOD_STORAGE")
public class IlliquidGoodStorage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Good good;

    @Column(nullable = false)
    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
