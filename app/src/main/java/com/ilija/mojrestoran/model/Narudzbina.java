package com.ilija.mojrestoran.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Narudzbina {

    private boolean naplacena;
    private Date datum;
    private Sto sto;
    private Korisnik korisnik;
    private HashMap<Stavka, Integer> stavka = new HashMap<>();
    private ArrayList<Racun> racunArrayList;

    public Narudzbina(boolean naplacena, Date datum, Sto sto, Korisnik korisnik, HashMap<Stavka, Integer> stavka, ArrayList<Racun> racunArrayList) {
        this.naplacena = naplacena;
        this.datum = datum;
        this.sto = sto;
        this.korisnik = korisnik;
        this.stavka = stavka;
        this.racunArrayList = racunArrayList;
    }

    public boolean isNaplacena() {
        return naplacena;
    }

    public void setNaplacena(boolean naplacena) {
        this.naplacena = naplacena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public HashMap<Stavka, Integer> getStavka() {
        return stavka;
    }

    public void setStavka(HashMap<Stavka, Integer> stavka) {
        this.stavka = stavka;
    }

    public ArrayList<Racun> getRacunArrayList() {
        return racunArrayList;
    }

    public void setRacunArrayList(ArrayList<Racun> racunArrayList) {
        this.racunArrayList = racunArrayList;
    }
}
