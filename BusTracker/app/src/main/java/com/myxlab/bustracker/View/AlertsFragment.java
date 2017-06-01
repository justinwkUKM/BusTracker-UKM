package com.myxlab.bustracker.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myxlab.bustracker.Controller.AlertsAdapter;
import com.myxlab.bustracker.Model.AlertsData;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.List;

public class AlertsFragment extends Fragment {

    private RecyclerView recyclerViewAlerts;
    private List<AlertsData> alertsDatasList = new ArrayList<>();
    private AlertsAdapter alertsAdapter;

    public AlertsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);
        recyclerViewAlerts = (RecyclerView) view.findViewById(R.id.recyclerViewAlerts);
        initVar();
        populateData();
        return view;
    }

    private void initVar() {
        alertsAdapter = new AlertsAdapter(alertsDatasList, getActivity());
        recyclerViewAlerts.setHasFixedSize(true);
        recyclerViewAlerts.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAlerts.setLayoutManager(mLayoutManager);
        recyclerViewAlerts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAlerts.setAdapter(alertsAdapter);
    }

    private void populateData(){

        AlertsData alertsData = new AlertsData("Bus Zone 2 Delay (26/10/2017 5:30PM)", "Delay due to traffic");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (26/10/2017 4:30PM)", "Delay due to traffic");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (26/10/2017 4:10PM)", "Delay due to close road");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 6 Delay (26/10/2017 3:22PM)", "Delay due to tyre punctured");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (24/10/2017 12:30PM)", "Delay due to traffic");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (22/10/2017 10:30PM)", "Delay due to close road");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 6 (22/10/2017 6:30PM)", "Delay due to tyre punctured");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (21/10/2017 5:30PM)", "Delay due to traffic");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (19/10/2017 8:30PM)", "Delay due to close road");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 6 Delay (17/10/2017 4:11PM)", "Delay due to tyre punctured");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 2 Delay (30/9/2017 4:42PM)", "Delay due to traffic");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 3U Delay (26/9/2017 3:00PM)", "Delay due to close road");
        alertsDatasList.add(alertsData);

        alertsData = new AlertsData("Bus Zone 2 Delay (26/9/2017 1:57PM)", "Delay due to tyre punctured");
        alertsDatasList.add(alertsData);

        alertsAdapter.notifyDataSetChanged();
    }

}
