package ru.ifmo.cis.mrp.back.ejb;

import ru.ifmo.cis.mrp.entity.Good;

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

}
