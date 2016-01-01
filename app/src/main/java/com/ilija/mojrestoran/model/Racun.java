package com.ilija.mojrestoran.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Racun {

    private String id;
    private Narudzbina narudzbina;
    private Date datum;
    private double cena;
    private HashMap<Stavka, Integer> stavka = new HashMap<>();

    public Racun(String id, Narudzbina narudzbina, Date datum, double cena, HashMap<Stavka, Integer> stavka) {
        this.id = id;
        this.narudzbina = narudzbina;
        this.datum = datum;
        this.cena = cena;
        this.stavka = stavka;
    }

    public String getId() {
        return id;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public Date getDatum() {
        return datum;
    }

    public double getCena() {
        return cena;
    }

    public HashMap<Stavka, Integer> getStavka() {
        return stavka;
    }
}
