package com.begers.tariflerim.utiles;

import com.begers.tariflerim.model.roomdb.Tarif;

import java.util.Comparator;

public class TarifComparator implements Comparator<Tarif> {

    @Override
    public int compare(Tarif t1, Tarif t2) {
        return t2.getDate().compareTo(t1.getDate());
    }
}
