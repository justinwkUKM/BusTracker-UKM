package com.driverapp.View;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;
import com.driverapp.Service.LocationListenerService;
import com.driverapp.ServiceCallbacks;

import java.util.Calendar;

public class TrackActivity extends AppCompatActivity implements ServiceCallbacks{

    public static final String SETUP_FRAGMENT = "Setup Fragment";
    private boolean status = true;
    public LocationManager locationManager;
    private TickTockView tickTockView;
    boolean startup = false;
    private Button startJourneyButton;
    private ImageView trackerIcon;
    private int minutesResume, secondsResume;
    public boolean settingUP = false;
    Context context;
    public boolean statusOfGPS;
    public ConnectivityManager connectivityManager;
    private TextView tvNextBusStop;
    private LocationListenerService locationListenerService;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        context = getApplicationContext();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        startJourneyButton = (Button) findViewById(R.id.start_track_journey_btn);
        trackerIcon = (ImageView) findViewById(R.id.tracker_icon);
        tvNextBusStop = (TextView) findViewById(R.id.track_next_bus);

        tickTockView = (TickTockView) findViewById(R.id.count);
        if (tickTockView != null) {
            tickTockView.setOnTickListener(new TickTockView.OnTickListener() {

                @Override
                public String getText(long timeRemaining) {

                    if (timeRemaining == 0 && startup) {
                        startup = false;
                        toggleTimer();
                    }

                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;

                    minutesResume = minutes;
                    secondsResume = seconds;

                    return String.format("%2$02d%5$s %3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");

                }
            });
        }
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.stop_your_journey);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                status = true;
                startJourneyButton.setText(R.string.resume_journey);
                dialog.dismiss();
                startJourneyButton.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
                if (bound) {
                    locationListenerService.setCallbacks(null);
                    unbindService(serviceConnection);
                    bound = false;
                }
                tickTockView.stop();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        if (!settingUP) {
            Toast.makeText(this, R.string.please_wait, Toast.LENGTH_SHORT).show();
        } else {

            if (status) {

                if (minutesResume != 0) {
                    inProgressDialog();
                } else {
                    finish();
                    super.onBackPressed();
                }
            } else {
                Toast.makeText(this, R.string.please_stop_journey, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startUpTimer() {

        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, 1);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, 0);
        if (tickTockView != null) {
            tickTockView.start(start, end);
        }
    }

    private void toggleTimer() {

        Calendar end = Calendar.getInstance();

        if (minutesResume == 0) {
            end.add(Calendar.MINUTE, 40);
            end.add(Calendar.SECOND, 0);
        } else {
            end.add(Calendar.MINUTE, minutesResume);
            end.add(Calendar.SECOND, secondsResume);
        }

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, 0);
        if (tickTockView != null) {
            tickTockView.start(start, end);
        }
    }

    public void startJourney(View view) {

        trackJourney();
    }


    public void trackJourney() {

        boolean networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

        if (!networkCheck) {
            Toast.makeText(context, R.string.check_internet, Toast.LENGTH_SHORT).show();
        } else {

            if (!settingUP) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.start_your_journey);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.setup_fragment_layout, new SetupFragment()).addToBackStack(SETUP_FRAGMENT).commit();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                trackBus();
            }

        }
    }

    private void inProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cancel_current_progress);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                minutesResume = 0;
                onBackPressed();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDetailsJourney(View view) {

        String[] list = new String[]{"Driver ID : " + UserInstance.getInstance().getDriver().getDriver_id().toUpperCase(), "Bus Plate : " + UserInstance.getInstance().getBus().getBusPlate(), "Route : " + UserInstance.getInstance().getRoute().getRouteName()};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle(R.string.journey_detail);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //nothing to show
            }
        }).show();

    }

    public void trackBus() {

        if (status) {
            status = false;
            toggleTimer();
            startJourneyButton.setText(R.string.stop_journey);
            trackerIcon.setVisibility(View.GONE);
            startJourneyButton.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
            Intent intent = new Intent(this, LocationListenerService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            createAndShowAlertDialog();
        }
    }

    public void nextBusStopLabel(String string){
        tvNextBusStop.setText("Next Bus Stop : "+string.toUpperCase());
    }

    @Override
    public void nextBusStop() {
        int nextBusStopIndex = UserInstance.getInstance().getBusLocation() + 1;
        UserInstance.getInstance().setBusLocation(nextBusStopIndex);
        nextBusStopLabel(UserInstance.getInstance().getRoute().getBusStopList().get(nextBusStopIndex).getName());
    }

    @Override
    public void finishJourney(Location location) {
        status = true;
        startJourneyButton.setVisibility(View.GONE);
        trackBus();
        tvNextBusStop.setText(R.string.finish_journey);
        if (bound) {
            locationListenerService.setCallbacks(null);
            unbindService(serviceConnection);
            bound = false;
        }
        tickTockView.stop();
        UserInstance.getInstance().getVolleyApp().setStatusBus(getString(R.string.url_bus_status),getApplicationContext(),false,location.getLatitude(),location.getLongitude());
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocationListenerService.LocalBinder binder = (LocationListenerService.LocalBinder) service;
            locationListenerService = binder.getService();
            bound = true;
            locationListenerService.setCallbacks(TrackActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
}
