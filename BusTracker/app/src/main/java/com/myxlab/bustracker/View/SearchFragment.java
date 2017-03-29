package com.myxlab.bustracker.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myxlab.bustracker.Controller.POIAdapter;
import com.myxlab.bustracker.DBHandler;
import com.myxlab.bustracker.Model.POI;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{

    public RecyclerView recyclerViewPOI;
    private List<POI> poiArrayList = new ArrayList<>();
    private POIAdapter poiAdapter;
    public LinearLayout searchLayout;
    private TextView tvSearchNotFound;
    public LinearLayout ll_expand_layout_category;
    public ImageView iv_library_expand_ico;
    public TextView tv_library_expand_text;
    public FragmentManager fragmentManager;

    public SearchFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        fragmentManager = getFragmentManager();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        initVar(view);
        expandLayout(view);
        populateData();
        ((MainActivity)getActivity()).searchFragment = this;

        if (((MainActivity)getActivity()).rv_search_chip.getVisibility() == View.VISIBLE){

            ((MainActivity)getActivity()).searchCatRefreshList(((MainActivity)getActivity()).category, ((MainActivity)getActivity()).search.getText().toString(),getActivity());

        }

        return view;
    }

    private void expandLayout(View view){

        ll_expand_layout_category = (LinearLayout) view.findViewById(R.id.expand_layout_category);
        iv_library_expand_ico = (ImageView) view.findViewById(R.id.library_expand_ico);
        tv_library_expand_text = (TextView) view.findViewById(R.id.library_expand_text);
        RelativeLayout rl_less_expand = (RelativeLayout) view.findViewById(R.id.less_expand);

        rl_less_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_expand_layout_category.setVisibility(View.GONE);
                iv_library_expand_ico.setImageResource(R.drawable.ic_expand_more);
                tv_library_expand_text.setText(R.string.more);
                ((MainActivity)getActivity()).expand_toggle = false;

            }
        });

    }

    public void refreshList(List<POI> poiList, String key) {

        poiArrayList.clear();

        if (poiList == null) {
            searchLayout.setVisibility(View.GONE);
            tvSearchNotFound.setVisibility(View.GONE);
        } else {
            searchLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < poiList.size(); i++) {
                POI poi = new POI(poiList.get(i).getName(), poiList.get(i).getLon(), poiList.get(i).getLon(), poiList.get(i).getType(), poiList.get(i).getCode());
                poiArrayList.add(poi);
            }

            if (poiList.size() == 0){

                tvSearchNotFound.setVisibility(View.VISIBLE);
                tvSearchNotFound.setText("No results found for '" + key +"'");


            } else {

                tvSearchNotFound.setVisibility(View.GONE);

            }

            poiAdapter.notifyDataSetChanged();
        }

    }

    private void initVar(View view) {

        recyclerViewPOI = (RecyclerView) view.findViewById(R.id.rv_search);
        searchLayout = (LinearLayout) view.findViewById(R.id.search_layout);
        tvSearchNotFound = (TextView) view.findViewById(R.id.searchNotFound);

        poiAdapter = new POIAdapter(poiArrayList, getActivity(),((MainActivity)getActivity()), this);
        recyclerViewPOI.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPOI.setLayoutManager(mLayoutManager);
        recyclerViewPOI.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPOI.setAdapter(poiAdapter);
    }

    public void populateData() {

        try {
            poiArrayList.clear();
            DBHandler dbHandler = new DBHandler(getActivity().getApplicationContext(),null);
            refreshList(dbHandler.getPOIs(),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        ((MainActivity)getActivity()).tabLayout.setVisibility(View.GONE);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {

        if (((MainActivity)getActivity()).tabLayout.getVisibility() == View.GONE){
            ((MainActivity)getActivity()).tabLayout.setVisibility(View.VISIBLE);
        }

        ((MainActivity)getActivity()).expand_toggle = false;

        super.onDestroy();
    }
}
