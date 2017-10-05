package com.myxlab.bustracker.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myxlab.bustracker.Controller.AlertsAdapter;
import com.myxlab.bustracker.Model.AlertsData;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Alerts fragment.
 */
public class AlertsFragment extends Fragment {

    private static final String TAG = AlertsFragment.class.getSimpleName();
    private RecyclerView recyclerViewAlerts;
    private List<AlertsData> alertsDatasList = new ArrayList<>();
    private AlertsAdapter alertsAdapter;

    /**
     * Instantiates a new Alerts fragment.
     */
    public AlertsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);
        recyclerViewAlerts = (RecyclerView) view.findViewById(R.id.recyclerViewAlerts);
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            Log.e(TAG, "visible && isResumed()");

            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            //onResume();
            callUrl();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            Log.e(TAG, "!getUserVisibleHint");

            return;
        }else
            Log.e(TAG, "getUserVisibleHint");

        callUrl();
        //INSERT CUSTOM CODE HERE

    }

    /**
     * Call url.
     */
    public void callUrl() {
        Log.e(TAG, "callURL");

        if(getActivity() != null){
            UserInstance.getInstance().getVolleyApp().getAlertsData(getString(R.string.url_get_alerts),getActivity(),this);
        }
    }

    private void initVar() {
        alertsAdapter = new AlertsAdapter(alertsDatasList, getActivity());
        recyclerViewAlerts.setHasFixedSize(true);
        recyclerViewAlerts.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerViewAlerts.setLayoutManager(mLayoutManager);
        recyclerViewAlerts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAlerts.setAdapter(alertsAdapter);
    }


    /**
     * Populate data.
     *
     * @param alertsDatasLists the alerts datas lists
     */
    public void populateData( List<AlertsData> alertsDatasLists){

        alertsDatasList = alertsDatasLists;
        initVar();
        /*AlertsData alertsData = new AlertsData("Bus Zone 2 Delay (26/10/2017 5:30PM)", "Delay due to traffic");
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

        alertsAdapter.notifyDataSetChanged();*/
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Log.e(TAG, ((Object) this).getClass().getSimpleName() + " is NOT on screen");
        }
        else
        {
            Log.e(TAG, ((Object) this).getClass().getSimpleName() + " is on screen");
        }
    }

}
