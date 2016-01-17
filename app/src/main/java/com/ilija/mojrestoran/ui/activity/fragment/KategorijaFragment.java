package com.ilija.mojrestoran.ui.activity.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ilija.mojrestoran.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KategorijaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KategorijaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button button;

    public KategorijaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KategorijaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KategorijaFragment newInstance() {
        return new KategorijaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategorija, container, false);
        button = (Button) view.findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                PodkategorijaFragment page2 = (PodkategorijaFragment) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 1);
                page2.setString("vrh brate");
                TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.sliding_tabs);
//                viewPager.setTag("id 111");
//                viewPager.setCurrentItem(1);
//                tabLayout.setupWithViewPager(viewPager);
//                tabLayout.setScrollPosition(1, 0f, true);
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
