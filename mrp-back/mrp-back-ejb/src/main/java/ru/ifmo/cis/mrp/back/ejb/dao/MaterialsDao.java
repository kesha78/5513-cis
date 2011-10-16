package ru.ifmo.cis.mrp.back.ejb.dao;

import ru.ifmo.cis.mrp.entity.Material;

import javax.ejb.Local;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.10.11
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MaterialsDao {

    public void create(Material material);

    public Collection<Material> read();

}
