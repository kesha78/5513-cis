package ru.ifmo.cis.mrp.back.web;

import ru.ifmo.cis.mrp.back.ejb.dao.MaterialsDao;
import ru.ifmo.cis.mrp.entity.Material;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.10.11
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class TestBean implements Serializable{

    @EJB
    MaterialsDao materialsDao;

    public void addMaterial(String name,Long price){
        Material material = new Material();
        material.setName(name);
        material.setPrice(price);
        materialsDao.create(material);
    }
    public Collection<Material> getMaterials(){
        return materialsDao.read();
    }

}
