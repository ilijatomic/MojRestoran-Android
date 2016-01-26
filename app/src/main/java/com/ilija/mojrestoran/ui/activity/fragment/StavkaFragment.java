package com.ilija.mojrestoran.ui.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.model.Podkategorija;

/**
 */
public class StavkaFragment extends Fragment {

    public StavkaFragment() {}

    public static StavkaFragment newInstance() {
        return new StavkaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stavka, container, false);
    }


    public void setPodkategorija(Podkategorija podkategorija) {}

}
