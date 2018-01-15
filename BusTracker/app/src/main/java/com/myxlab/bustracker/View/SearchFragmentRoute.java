package com.myxlab.bustracker.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myxlab.bustracker.Controller.BusAdapter;
import com.myxlab.bustracker.Controller.RouteAdapter;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.Route;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by MyXLab on 30/1/2017.
 * Fragment to fetch the Bus and Route data from backend
 */

public class SearchFragmentRoute extends Fragment {


    List<Route> routeList;
    View progressBar;
    RecyclerView recyclerView;
    Context context;
    TextView searchStatus;
    private EditText search;
    private ImageView clear_search;
    private String key = null;
    RouteAdapter routeAdapter;
    FragmentManager fragmentManager;

    public SearchFragmentRoute() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_route, container, false);


        context = getActivity().getApplicationContext();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        fragmentManager = getFragmentManager();

        progressBar = view.findViewById(R.id.search_progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchStatus = (TextView) view.findViewById(R.id.searchStatus);

        search = (EditText) view.findViewById(R.id.search_text);
        clear_search = (ImageView) view.findViewById(R.id.clear_search);
        clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });

        textWatcher();

        Bundle extras = getArguments();
        key = extras.getString(RouteList.SEARCH_KEY);
        checkKey(key);

        return view;
    }

    private void checkKey(String key){

        switch (key){


            case "Route" :
                search.setHint("Select Route");
                UserInstance.getInstance().getVolleyApp().getRouteListJoruney(getString(R.string.url_route), progressBar,context, this);
                break;
        }

    }



    public void populateRoute(List<Route> route){

        searchStatus.setVisibility(View.GONE);
        routeList = route;
        routeAdapter = new RouteAdapter(routeList, fragmentManager, (RouteList) getActivity());
        recyclerView.setAdapter(routeAdapter);
        routeAdapter.notifyDataSetChanged();
    }

    private void textWatcher() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (charSequence != null && charSequence.length() != 0) {

                    int listSize = 0;

                    switch (key){


                        case "Route" :
                            List<Route> routeListSearched = searchRoute(charSequence.toString());
                            refreshListRoute(routeListSearched);
                            listSize = routeListSearched.size();
                            break;
                    }

                    if (listSize != 0){
                        searchStatus.setVisibility(View.GONE);
                    } else {
                        searchStatus.setVisibility(View.VISIBLE);
                        searchStatus.setText("No results found for '" + charSequence.toString() +"'");
                    }
                }

                if (search.getText().toString().length() != 0) {

                    clear_search.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (search.getText().toString().length() == 0){

                    clear_search.setVisibility(View.GONE);
                    searchStatus.setVisibility(View.GONE);

                    switch (key){


                        case "Route" :
                            refreshListRoute(routeList);
                            break;
                    }
                }

            }
        });
    }



    private List<Route> searchRoute(String key){
        List<Route> routeListNew = new LinkedList<>();

        for (int i = 0; i < routeList.size(); i++){

            if (routeList.get(i).getRouteName().toLowerCase().contains(key) || routeList.get(i).getRouteName().toUpperCase().contains(key)){
                routeListNew.add(routeList.get(i));
            }

        }

        return routeListNew;
    }



    private void refreshListRoute(List<Route> route) {

        routeAdapter.setRouteList(route);
        routeAdapter.notifyDataSetChanged();

    }

}
