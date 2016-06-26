package com.ilija.mojrestoran.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Narudzbina {

    private String id;
    private boolean naplacena;
    private Date datum;
    private Sto sto;
    private Korisnik korisnik;
    private ArrayList<NaruceneStavke> nenaplaceneStavke;
    private ArrayList<Racun> racunArrayList;

    public Narudzbina() {
    }

    public Narudzbina(String id, boolean naplacena, Date datum, Sto sto, Korisnik korisnik, ArrayList<NaruceneStavke> nenaplaceneStavke, ArrayList<Racun> racunArrayList) {
        this.id = id;
        this.naplacena = naplacena;
        this.datum = datum;
        this.sto = sto;
        this.korisnik = korisnik;
        this.nenaplaceneStavke = nenaplaceneStavke;
        this.racunArrayList = racunArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<NaruceneStavke> getNenaplaceneStavke() {
        if (nenaplaceneStavke == null)
            nenaplaceneStavke = new ArrayList<>();
        return nenaplaceneStavke;
    }

    public void setNenaplaceneStavke(ArrayList<NaruceneStavke> nenaplaceneStavke) {
        this.nenaplaceneStavke = nenaplaceneStavke;
    }

    public ArrayList<Racun> getRacunArrayList() {
        if (racunArrayList == null)
            racunArrayList = new ArrayList<>();
        return racunArrayList;
    }

    public void setRacunArrayList(ArrayList<Racun> racunArrayList) {
        this.racunArrayList = racunArrayList;
    }
}
