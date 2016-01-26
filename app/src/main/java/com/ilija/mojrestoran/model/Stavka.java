package com.ilija.mojrestoran.model;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Stavka {

    private String id;
    private String naziv;
    private double cena;
    private Podkategorija podkategorija;

    public Stavka(String id, String naziv, double cena, Podkategorija podkategorija) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
        this.podkategorija = podkategorija;
    }

    public String getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getCena() {
        return cena;
    }

    public Podkategorija getPodkategorija() {
        return podkategorija;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
