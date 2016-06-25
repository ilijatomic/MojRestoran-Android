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

    public static void populateSpinnerStrings(ArrayList<String> strings, ArrayList<?> objects, String first) {

        strings.clear();
        if (!first.isEmpty())
            strings.add(first);
        if (objects.size() > 0) {
            for (Object o : objects) {
                strings.add(o.toString());
            }
        }
    }
}
