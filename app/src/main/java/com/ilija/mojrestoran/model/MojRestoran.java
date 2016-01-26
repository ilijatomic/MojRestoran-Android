package com.ilija.mojrestoran.model;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/2/2016.
 */
public class MojRestoran {

    private ArrayList<Korisnik> korisnikArrayList = new ArrayList<>();
    private ArrayList<Kategorija> kategorijaArrayList;
    private ArrayList<Podkategorija> podkategorijaArrayList;
    private ArrayList<Stavka> stavkaArrayList;
    private ArrayList<Sto> stoArrayList;
    private ArrayList<Narudzbina> narudzbinaArrayList;

    public MojRestoran(ArrayList<Korisnik> korisnikArrayList, ArrayList<Kategorija> kategorijaArrayList,
                       ArrayList<Podkategorija> podkategorijaArrayList, ArrayList<Stavka> stavkaArrayList,
                       ArrayList<Sto> stoArrayList, ArrayList<Narudzbina> narudzbinaArrayList) {
        this.korisnikArrayList = korisnikArrayList;
        this.kategorijaArrayList = kategorijaArrayList;
        this.podkategorijaArrayList = podkategorijaArrayList;
        this.stavkaArrayList = stavkaArrayList;
        this.stoArrayList = stoArrayList;
        this.narudzbinaArrayList = narudzbinaArrayList;
    }

    public ArrayList<Korisnik> getKorisnikArrayList() {
        return korisnikArrayList;
    }

    public ArrayList<Kategorija> getKategorijaArrayList() {
        if (kategorijaArrayList == null)
            kategorijaArrayList = new ArrayList<>();
        return kategorijaArrayList;
    }

    public ArrayList<Sto> getStoArrayList() {
        if (stoArrayList == null)
            stoArrayList = new ArrayList<>();
        return stoArrayList;
    }

    public ArrayList<Narudzbina> getNarudzbinaArrayList() {
        if (narudzbinaArrayList == null)
            narudzbinaArrayList = new ArrayList<>();
        return narudzbinaArrayList;
    }

    public ArrayList<Podkategorija> getPodkategorijaArrayList() {
        if (podkategorijaArrayList == null)
            podkategorijaArrayList = new ArrayList<>();
        return podkategorijaArrayList;
    }

    public ArrayList<Stavka> getStavkaArrayList() {
        if (stavkaArrayList == null)
            stavkaArrayList = new ArrayList<>();
        return stavkaArrayList;
    }
}
