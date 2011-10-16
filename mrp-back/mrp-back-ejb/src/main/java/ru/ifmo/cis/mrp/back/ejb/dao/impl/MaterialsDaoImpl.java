package ru.ifmo.cis.mrp.back.ejb.dao.impl;

import ru.ifmo.cis.mrp.back.ejb.dao.MaterialsDao;
import ru.ifmo.cis.mrp.entity.Material;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.10.11
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class MaterialsDaoImpl implements MaterialsDao{

    @PersistenceContext(unitName = "MRPPersistenceUnit")
    private EntityManager em;

    @Override
    public void create(Material material) {
        em.persist(material);
    }

    @Override
    public Collection<Material> read() {
        return em.createQuery("from Material").getResultList();
    }
}
