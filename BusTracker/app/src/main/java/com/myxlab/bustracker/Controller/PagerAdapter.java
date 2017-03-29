package com.myxlab.bustracker.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.myxlab.bustracker.View.AlertsFragment;
import com.myxlab.bustracker.View.MapsFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
    private ArrayList<String> mTabHeader;

    public PagerAdapter(FragmentManager fm, ArrayList<String> tabHeader) {
        super(fm);
        this.mTabHeader = tabHeader;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MapsFragment();
            case 1:
                return new AlertsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabHeader.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return mTabHeader.get(position);
        return null;
    }
}
