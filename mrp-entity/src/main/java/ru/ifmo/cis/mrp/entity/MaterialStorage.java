package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MATERIALS_STORAGE")
public class MaterialStorage implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Material material;

    @Column(nullable = false)
    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
