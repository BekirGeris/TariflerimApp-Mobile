package com.begers.tariflerim.utiles;

import com.begers.tariflerim.model.Tarif;

import java.util.Comparator;
import java.util.Date;

public class TarifComparator implements Comparator<Tarif> {

    @Override
    public int compare(Tarif t1, Tarif t2) {
        return t2.getDate().compareTo(t1.getDate());
    }
}
