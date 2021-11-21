package com.begers.tariflerim.utiles;

import com.begers.tariflerim.model.roomdb.TarifRoom;

import java.util.Comparator;

public class TarifComparator implements Comparator<TarifRoom> {

    @Override
    public int compare(TarifRoom t1, TarifRoom t2) {
        return t2.getDate().compareTo(t1.getDate());
    }
}
