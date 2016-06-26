package com.ilija.mojrestoran.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Racun {

    private String id;
    private Date datum;
    private double cena;
    private ArrayList<NaruceneStavke> naplaceneStavke;

    public Racun() {
    }

    public Racun(String id, Date datum, double cena, ArrayList<NaruceneStavke> naplaceneStavke) {
        this.id = id;
        this.datum = datum;
        this.cena = cena;
        this.naplaceneStavke = naplaceneStavke;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public ArrayList<NaruceneStavke> getNaplaceneStavke() {
        if (naplaceneStavke == null)
            naplaceneStavke = new ArrayList<>();
        return naplaceneStavke;
    }

    public void setNaplaceneStavke(ArrayList<NaruceneStavke> naplaceneStavke) {
        this.naplaceneStavke = naplaceneStavke;
    }
}
