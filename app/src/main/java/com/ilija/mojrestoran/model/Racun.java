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
    private ArrayList<NaruceneStavke> naruceneStavkeArrayList;

    public Racun(String id, Narudzbina narudzbina, Date datum, double cena, ArrayList<NaruceneStavke> naruceneStavkeArrayList) {
        this.id = id;
        this.narudzbina = narudzbina;
        this.datum = datum;
        this.cena = cena;
        this.naruceneStavkeArrayList = naruceneStavkeArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
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

    public ArrayList<NaruceneStavke> getNaruceneStavkeArrayList() {
        return naruceneStavkeArrayList;
    }

    public void setNaruceneStavkeArrayList(ArrayList<NaruceneStavke> naruceneStavkeArrayList) {
        this.naruceneStavkeArrayList = naruceneStavkeArrayList;
    }
}
