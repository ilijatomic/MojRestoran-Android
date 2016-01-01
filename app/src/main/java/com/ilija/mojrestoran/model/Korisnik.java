package com.ilija.mojrestoran.model;

/**
 * Created by Ilija on 1/2/2016.
 */
public class Korisnik {

    private String id;
    private String ime;
    private String prezime;
    private String brTel;
    private String email;
    private String password;
    private String tip;

    public Korisnik(String id, String ime, String prezime, String brTel, String email, String password, String tip) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.brTel = brTel;
        this.email = email;
        this.password = password;
        this.tip = tip;
    }

    public String getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getBrTel() {
        return brTel;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTip() {
        return tip;
    }
}
