package com.ilija.mojrestoran.ui.activity.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ilija on 1/17/2016.
 */
public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;

    private String tabTitles[] = new String[] {"KATEGORIJA", "PODKATEGORIJA", "STAVKA"};
    private Context context;

    public SimpleFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return KategorijaFragment.newInstance();
            case 1:
                return PodkategorijaFragment.newInstance("1", "2");
        }
        return KategorijaFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
