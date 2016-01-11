package com.ilija.mojrestoran.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ilija.tomic on 1/5/2016.
 */
public class ToastMessage {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
