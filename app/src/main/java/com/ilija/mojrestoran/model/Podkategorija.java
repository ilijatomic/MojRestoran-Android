package com.ilija.mojrestoran.model;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Podkategorija {

    private String id;
    private String naziv;
    private Kategorija kategorija;

    public Podkategorija(String id, String naziv, Kategorija kategorija) {
        this.id = id;
        this.naziv = naziv;
        this.kategorija = kategorija;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
