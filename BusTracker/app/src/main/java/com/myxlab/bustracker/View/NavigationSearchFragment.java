package com.myxlab.bustracker.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.myxlab.bustracker.R;


public class NavigationSearchFragment extends Fragment {

    public NavigationSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_search, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {

        ((NavigationActivity)getActivity()).appBarLayout.setVisibility(View.VISIBLE);

        super.onDestroy();
    }
}
