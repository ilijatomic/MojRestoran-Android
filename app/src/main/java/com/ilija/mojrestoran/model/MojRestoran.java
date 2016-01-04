package com.ilija.mojrestoran.model;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/2/2016.
 */
public class MojRestoran {
    private ArrayList<Korisnik> korisnikArrayList = new ArrayList<>();
    private ArrayList<Kategorija> kategorijaArrayList = new ArrayList<>();
    private ArrayList<Sto> stoArrayList = new ArrayList<>();
    private ArrayList<Narudzbina> narudzbinaArrayList = new ArrayList<>();

    public MojRestoran(ArrayList<Korisnik> korisnikArrayList, ArrayList<Kategorija> kategorijaArrayList, ArrayList<Sto> stoArrayList, ArrayList<Narudzbina> narudzbinaArrayList) {
        this.korisnikArrayList = korisnikArrayList;
        this.kategorijaArrayList = kategorijaArrayList;
        this.stoArrayList = stoArrayList;
        this.narudzbinaArrayList = narudzbinaArrayList;
    }

    public ArrayList<Korisnik> getKorisnikArrayList() {
        return korisnikArrayList;
    }

    public ArrayList<Kategorija> getKategorijaArrayList() {
        return kategorijaArrayList;
    }

    public ArrayList<Sto> getStoArrayList() {
        return stoArrayList;
    }

    public ArrayList<Narudzbina> getNarudzbinaArrayList() {
        return narudzbinaArrayList;
    }
}
