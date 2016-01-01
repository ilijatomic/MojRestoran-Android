package com.ilija.mojrestoran.model;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Kategorija {

    private String id;
    private String naziv;
    private ArrayList<Podkategorija> podkategorijaArrayList = new ArrayList<>();

    public Kategorija(String id, String naziv, ArrayList<Podkategorija> podkategorijaArrayList) {
        this.id = id;
        this.naziv = naziv;
        this.podkategorijaArrayList = podkategorijaArrayList;
    }

    public String getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public ArrayList<Podkategorija> getPodkategorijaArrayList() {
        return podkategorijaArrayList;
    }
}
