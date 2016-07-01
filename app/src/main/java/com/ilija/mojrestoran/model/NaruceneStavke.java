package com.ilija.mojrestoran.model;

/**
 * Created by Ilija on 1/28/2016.
 */
public class NaruceneStavke {

    private Stavka stavka;
    private Integer kolicina = 0;

    public NaruceneStavke() {
    }

    public NaruceneStavke(Stavka stavka, Integer kolicina) {
        this.stavka = stavka;
        this.kolicina = kolicina;
    }

    public Stavka getStavka() {
        return stavka;
    }

    public void setStavka(Stavka stavka) {
        this.stavka = stavka;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }
}
