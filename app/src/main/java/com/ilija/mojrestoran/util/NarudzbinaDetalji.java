package com.ilija.mojrestoran.util;

import com.ilija.mojrestoran.model.Stavka;

import java.util.Date;

/**
 * Created by ilija.tomic on 2/1/2016.
 */
public class NarudzbinaDetalji {

    public enum DetaljiType {
        HEADER,
        STAVKE
    }

    private DetaljiType detaljiType;
    private double headerUkupno;
    private int stavkaKolicina;
    private Stavka stavka;
    private Date naplacen;

    public NarudzbinaDetalji(DetaljiType detaljiType, double headerUkupno, int stavkaKolicina, Stavka stavka, Date naplacen) {
        this.detaljiType = detaljiType;
        this.headerUkupno = headerUkupno;
        this.stavkaKolicina = stavkaKolicina;
        this.stavka = stavka;
        this.naplacen = naplacen;

    }

    public Date getNaplacen() {
        return naplacen;
    }

    public DetaljiType getDetaljiType() {
        return detaljiType;
    }

    public double getHeaderUkupno() {
        return headerUkupno;
    }

    public int getStavkaKolicina() {
        return stavkaKolicina;
    }

    public Stavka getStavka() {
        return stavka;
    }
}
