package ru.ifmo.cis.mrp.back.ejb.dao;

import ru.ifmo.cis.mrp.entity.Order;

import javax.ejb.Local;

/**
 * User: Igor
 * Date: 20.11.11
 * Time: 13:20
 */
@Local
public interface OrderDao {
    Order create(Order order);
}
