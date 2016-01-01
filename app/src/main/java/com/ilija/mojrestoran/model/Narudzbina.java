package com.ilija.mojrestoran.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Narudzbina {

    private Date datum;
    private Sto sto;
    private Korisnik korisnik;
    private HashMap<Stavka, Integer> stavka = new HashMap<>();
    private ArrayList<Racun> racunArrayList = new ArrayList<>();

    public Narudzbina(Date datum, Sto sto, Korisnik korisnik, HashMap<Stavka, Integer> stavka, ArrayList<Racun> racunArrayList) {
        this.datum = datum;
        this.sto = sto;
        this.korisnik = korisnik;
        this.stavka = stavka;
        this.racunArrayList = racunArrayList;
    }

    public Date getDatum() {
        return datum;
    }

    public Sto getSto() {
        return sto;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public HashMap<Stavka, Integer> getStavka() {
        return stavka;
    }

    public ArrayList<Racun> getRacunArrayList() {
        return racunArrayList;
    }
}
