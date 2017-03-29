package com.driverapp.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.driverapp.Model.UserInstance;
import com.driverapp.R;

public class SetupFragment extends Fragment {

    private Context context;
    private RelativeLayout closeRecyclerView;
    public TextView setupTextView, checkingConTextView, driverTextView, busTextView, routeTextView;
    public View checkingConProgressBar, driverProgressBar, busProgressBar, routeProgressBar;
    private FragmentManager fragmentManager;
    boolean networkCheck;
    private ConnectivityManager connectivityManager;

    public SetupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        fragmentManager = getFragmentManager();

        View view = inflater.inflate(R.layout.fragment_setup, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        closeRecyclerView = (RelativeLayout) view.findViewById(R.id.close_fail_rl);
        connectivityManager = ((TrackActivity) getActivity()).connectivityManager;

        setupTextView = (TextView) view.findViewById(R.id.setup_detail_tv);
        checkingConTextView = (TextView) view.findViewById(R.id.checking_con_tv);
        driverTextView = (TextView) view.findViewById(R.id.setup_driver_tv);
        busTextView = (TextView) view.findViewById(R.id.setup_bus_tv);
        routeTextView = (TextView) view.findViewById(R.id.setup_route_tv);

        checkingConProgressBar = view.findViewById(R.id.checking_con_progress);
        driverProgressBar = view.findViewById(R.id.setup_driver_progress);
        busProgressBar = view.findViewById(R.id.setup_bus_progress);
        routeProgressBar = view.findViewById(R.id.setup_route_progress);

        networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

        CardView closeCardView = (CardView) view.findViewById(R.id.close_fail_cv);


        closeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TrackActivity) getActivity()).settingUP = false;
                fragmentManager.popBackStack();
            }
        });

        settingUP();

        return view;
    }

    private void settingUP() {

        ((TrackActivity) getActivity()).statusOfGPS = ((TrackActivity) getActivity()).locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!((TrackActivity) getActivity()).statusOfGPS) {
            setupTextView.setVisibility(View.VISIBLE);
            setupTextView.setText(R.string.gps_off);
            checkingConProgressBar.setVisibility(View.INVISIBLE);
            checkingConTextView.setText(R.string.fail);
            checkingConTextView.setVisibility(View.VISIBLE);
            checkingConTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            closeRecyclerView.setVisibility(View.VISIBLE);
        } else {

            if (!networkCheck) {

                failClose(checkingConTextView);

            } else {

                nextStep(checkingConTextView);

                networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

                if (!networkCheck) {
                    Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
                } else {

                    UserInstance.getInstance().getVolleyApp().setupDriver(getString(R.string.url_driver),context, driverTextView, this);

                }

            }
        }

    }


    public void failClose(TextView textView) {

        closeRecyclerView.setVisibility(View.VISIBLE);
        ((TrackActivity) getActivity()).settingUP = false;

        if (textView.getId() == checkingConTextView.getId()) {

            setupTextView.setText(R.string.internet_off);
            setupTextView.setVisibility(View.VISIBLE);
            checkingConProgressBar.setVisibility(View.INVISIBLE);
            checkingConTextView.setText(R.string.fail);
            checkingConTextView.setVisibility(View.VISIBLE);
            checkingConTextView.setTextColor(ContextCompat.getColor(context, R.color.red));

        } else if (textView.getId() == driverTextView.getId()) {

            driverTextView.setText(R.string.fail);
            driverTextView.setVisibility(View.VISIBLE);
            driverTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            driverProgressBar.setVisibility(View.INVISIBLE);


        } else if (textView.getId() == busTextView.getId()) {

            busTextView.setText(R.string.fail);
            busTextView.setVisibility(View.VISIBLE);
            busTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            busProgressBar.setVisibility(View.INVISIBLE);

        } else if (textView.getId() == routeTextView.getId()) {

            routeTextView.setText(R.string.fail);
            routeTextView.setVisibility(View.VISIBLE);
            routeTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
            routeProgressBar.setVisibility(View.INVISIBLE);
        }

    }

    public void nextStep(TextView textView) {

        if (textView.getId() == checkingConTextView.getId()) {

            checkingConProgressBar.setVisibility(View.INVISIBLE);
            checkingConTextView.setText(R.string.success);
            checkingConTextView.setVisibility(View.VISIBLE);
            checkingConTextView.setTextColor(ContextCompat.getColor(context, R.color.green));

            driverTextView.setVisibility(View.INVISIBLE);
            driverProgressBar.setVisibility(View.VISIBLE);

        } else if (textView.getId() == driverTextView.getId()) {

            driverProgressBar.setVisibility(View.INVISIBLE);
            driverTextView.setText(R.string.success);
            driverTextView.setVisibility(View.VISIBLE);
            driverTextView.setTextColor(ContextCompat.getColor(context, R.color.green));

            busTextView.setVisibility(View.INVISIBLE);
            busProgressBar.setVisibility(View.VISIBLE);

            networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

            if (!networkCheck) {
                Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
            } else {

                UserInstance.getInstance().getVolleyApp().checkingBus(getString(R.string.url_bus_location),context, busTextView, this);

            }


        } else if (textView.getId() == busTextView.getId()) {

            busProgressBar.setVisibility(View.INVISIBLE);
            busTextView.setText(R.string.success);
            busTextView.setVisibility(View.VISIBLE);
            busTextView.setTextColor(ContextCompat.getColor(context, R.color.green));

            routeTextView.setVisibility(View.INVISIBLE);
            routeProgressBar.setVisibility(View.VISIBLE);

            if (!networkCheck) {
                Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
            } else {

                UserInstance.getInstance().getVolleyApp().getNextBusStop(context, routeTextView, this);

            }


        } else if (textView.getId() == routeTextView.getId()) {

            routeProgressBar.setVisibility(View.INVISIBLE);
            routeTextView.setText(R.string.success);
            routeTextView.setVisibility(View.VISIBLE);
            routeTextView.setTextColor(ContextCompat.getColor(context, R.color.green));

            startTrack();

        }
    }

    public void nextBusStopLabel(String string){
        ((TrackActivity) getActivity()).nextBusStopLabel(string);
    }

    public void startTrack(){

        ((TrackActivity) getActivity()).startUpTimer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            ((TrackActivity) getActivity()).trackBus();
            fragmentManager.popBackStack();
            ((TrackActivity) getActivity()).settingUP = true;
            }
        }, 1100);

    }

}
