package com.ilija.mojrestoran.model;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Sto {

    private String id;
    private int broj;

    public Sto(String id, int broj) {
        this.id = id;
        this.broj = broj;
    }

    public String getId() {
        return id;
    }

    public int getBroj() {
        return broj;
    }
}
