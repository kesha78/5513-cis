package ru.ifmo.cis.mrp.front.ejb;

import ru.ifmo.cis.mrp.entity.Good;

import javax.ejb.Local;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 16.11.11
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GoodsBean {
    Collection<Good> getAllGoods();
}
