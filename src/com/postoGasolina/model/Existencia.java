package com.postoGasolina.model;

import java.util.List;

public class Existencia {

    public static Object objeto(List objeto, int pos) {

        if (objeto.size() > pos) {
            return objeto.get(pos);
        }
        else {
            return null;
        }

    }
}
