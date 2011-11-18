package ru.ifmo.cis.mrp.back.ejb;

import ru.ifmo.cis.mrp.entity.Good;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * User: Igor
 * Date: 16.11.11
 * Time: 16:34
 */
@Local
public interface SequenceOptimizer {
    List<Good> getSequence();

    List<Good> getSequence(Collection<Good> newCollection);

}
