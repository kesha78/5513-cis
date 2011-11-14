package ru.ifmo.cis.mrp.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 12.11.11
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MATERIALS_TO_GOODS")
public class MaterialsToGoods implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Good good;

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

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
