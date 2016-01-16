package com.ilija.mojrestoran.util;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Ilija on 1/16/2016.
 */
public class Utilities {

    public static boolean checkRequiredFields(ArrayList<EditText> listaPolja) {

        boolean success = true;
        for (EditText editText : listaPolja) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Obavezno polje!");
                success = false;
            }
        }
        return success;

    }
}
