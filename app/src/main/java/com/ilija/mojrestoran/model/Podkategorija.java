package com.ilija.mojrestoran.model;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Podkategorija {

    private String id;
    private String naziv;
    private Kategorija kategorija;
    private ArrayList<Stavka> stavkaArrayList = new ArrayList<>();

    public Podkategorija(String id, String naziv, Kategorija kategorija, ArrayList<Stavka> stavkaArrayList) {
        this.id = id;
        this.naziv = naziv;
        this.kategorija = kategorija;
        this.stavkaArrayList = stavkaArrayList;
    }

    public String getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public ArrayList<Stavka> getStavkaArrayList() {
        return stavkaArrayList;
    }
}
