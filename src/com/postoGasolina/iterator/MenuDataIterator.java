package com.postoGasolina.iterator;

import javafx.util.Pair;
import java.util.List;

public class MenuDataIterator implements Iterator{

    private List<Pair<String, Runnable>> itens;
    private int posicao;

    public MenuDataIterator(List<Pair<String, Runnable>> itens) {
        this.itens = itens;
        posicao = 0;
    }

    @Override
    public boolean hasNext() {
        return !(posicao >= itens.size() || itens.get(posicao) == null);
    }

    @Override
    public Object next() {
        //Object item = itens.get(posicao);
        Pair<String, Runnable> item = itens.get(posicao);
        posicao++;
        return item;
    }

}
