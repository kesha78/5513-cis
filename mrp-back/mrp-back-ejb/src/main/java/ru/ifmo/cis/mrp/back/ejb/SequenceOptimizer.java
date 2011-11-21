package ru.ifmo.cis.mrp.back.ejb;

import ru.ifmo.cis.mrp.entity.Good;
import ru.ifmo.cis.mrp.entity.SupplyRequest;

import javax.ejb.Local;
import java.util.LinkedList;

/**
 * User: Igor
 * Date: 16.11.11
 * Time: 16:34
 */
@Local
public interface SequenceOptimizer {

    LinkedList<Good> getSequence();

    SupplyRequest countSupplyRequest(int estimate);

    SupplyRequest countStatisticSupply(int estimate, SupplyRequest supplyRequest);

}
