package com.ilija.mojrestoran.util;

import com.ilija.mojrestoran.model.MojRestoran;

/**
 * Created by ilija.tomic on 1/4/2016.
 */
public class DB {

    private static DB instance;

    private MojRestoran mojRestoran;

    private DB() {};

    public static DB getInstance() {
        if (instance == null)
            instance = new DB();
        return instance;
    };

    public MojRestoran getMojRestoran() {
        return mojRestoran;
    }

    public void setMojRestoran(MojRestoran mojRestoran) {
        this.mojRestoran = mojRestoran;
    }
}
