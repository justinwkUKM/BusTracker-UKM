package com.driverapp.View;

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

import com.driverapp.Controller.BusAdapter;
import com.driverapp.Controller.RouteAdapter;
import com.driverapp.Model.Bus;
import com.driverapp.Model.Route;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;

import java.util.LinkedList;
import java.util.List;

public class SearchFragment extends Fragment {

    List<Bus> busList;
    List<Route> routeList;
    View progressBar;
    RecyclerView recyclerView;
    Context context;
    TextView searchStatus;
    private EditText search;
    private ImageView clear_search;
    private String key = null;
    BusAdapter busAdapter;
    RouteAdapter routeAdapter;
    FragmentManager fragmentManager;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


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
        key = extras.getString(JourneyActivity.SEARCH_KEY);
        checkKey(key);

        return view;
    }

    private void checkKey(String key){

        switch (key){

            case "Bus" :
                search.setHint(R.string.search_bus);
                UserInstance.getInstance().getVolleyApp().getBusList(getString(R.string.url_bus), progressBar,context, this);
                break;
            case "Route" :
                search.setHint(R.string.search_route);
                UserInstance.getInstance().getVolleyApp().getRouteList(getString(R.string.url_route), progressBar,context, this);
                break;
        }

    }

    public void populateBus(List<Bus> buses){

        searchStatus.setVisibility(View.GONE);
        busList = buses;
        busAdapter = new BusAdapter(busList, fragmentManager, ((JourneyActivity)getActivity()));
        recyclerView.setAdapter(busAdapter);
        busAdapter.notifyDataSetChanged();
    }

    public void populateRoute(List<Route> route){

        searchStatus.setVisibility(View.GONE);
        routeList = route;
        routeAdapter = new RouteAdapter(routeList, fragmentManager, (JourneyActivity) getActivity());
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

                        case "Bus" :
                            List<Bus> busListSearched = searchBusPlate(charSequence.toString());
                            refreshListBus(busListSearched);
                            listSize = busListSearched.size();
                            break;
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

                        case "Bus" :
                            refreshListBus(busList);
                            break;
                        case "Route" :
                            refreshListRoute(routeList);
                            break;
                    }
                }

            }
        });
    }

    private List<Bus> searchBusPlate(String key){
        List<Bus> busListsNew = new LinkedList<>();

        for (int i = 0; i < busList.size(); i++){

            if (busList.get(i).getBusPlate().toLowerCase().contains(key) || busList.get(i).getBusPlate().toUpperCase().contains(key)){
                busListsNew.add(busList.get(i));
            }

        }

        return busListsNew;
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

    private void refreshListBus(List<Bus> buses) {

        busAdapter.setBusList(buses);
        busAdapter.notifyDataSetChanged();

    }

    private void refreshListRoute(List<Route> route) {

        routeAdapter.setRouteList(route);
        routeAdapter.notifyDataSetChanged();

    }

}
