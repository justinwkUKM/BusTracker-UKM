package com.driverapp.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.driverapp.BaseActivity;
import com.driverapp.Controller.BusStopAdapter;
import com.driverapp.Model.Bus;
import com.driverapp.Model.BusStop;
import com.driverapp.Model.Route;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;

import java.util.List;

public class JourneyActivity extends BaseActivity{
//testcomment
    public static final String SEARCH_FRAGMENT = "Search Fragment";
    public static final String SEARCH_KEY = "Search KEY";
    private static final String KEY_ROUTE = "Route";
    private static final String KEY_BUS = "Bus";
    TextView greed;
    TextView selectBus, selectRoute;
    LinearLayout routeOverview;
    RecyclerView routeRecyclerView;
    List<BusStop> busStopList;
    BusStopAdapter busStopAdapter;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        greed = (TextView) findViewById(R.id.greed_journey);
        String iniGreed = "Hi Driver";
        //usually error below line
        if (UserInstance.getInstance().getDriver().getDriver_id() != null){
            iniGreed = "Hi, " + UserInstance.getInstance().getDriver().getDriver_id();
        }
        greed.setText(iniGreed);

        selectBus = (TextView) findViewById(R.id.tv_select_bus);
        selectRoute = (TextView) findViewById(R.id.tv_select_route);

        routeOverview = (LinearLayout) findViewById(R.id.route_list_details);

        routeRecyclerView = (RecyclerView) findViewById(R.id.route_list_details_rv);
        routeRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        routeRecyclerView.setLayoutManager(mLayoutManager);
        routeRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public void nextButton(View view) {

        if (UserInstance.getInstance().getBus().getBusId() == null || UserInstance.getInstance().getRoute().getRouteId() == null){
            Toast.makeText(this, R.string.select_bus_n_route, Toast.LENGTH_SHORT).show();
        } else if (UserInstance.getInstance().getBus() == null){
            Toast.makeText(this, R.string.select_bus, Toast.LENGTH_SHORT).show();
        } else if (UserInstance.getInstance().getRoute() == null){
            Toast.makeText(this, R.string.select_route, Toast.LENGTH_SHORT).show();
        } else{
            Intent intent = new Intent(JourneyActivity.this, TrackActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

        /*Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_layout);

        if (currentFragment instanceof SearchFragment) {
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }*/
    }

    public void setBus(Bus busInfo){
        UserInstance.getInstance().setBus(busInfo);
        selectBus.setText(busInfo.getBusName() + "(" + busInfo.getBusPlate() + ")" );
    }

    public void setRoute(Route routeInfo){
        UserInstance.getInstance().setRoute(routeInfo);
        routeOverview.setVisibility(View.VISIBLE);
        selectRoute.setText(routeInfo.getRouteName());

        busStopList = routeInfo.getBusStopList();
        busStopAdapter = new BusStopAdapter(busStopList);
        routeRecyclerView.setAdapter(busStopAdapter);
        busStopAdapter.notifyDataSetChanged();
    }

    public View onSearchClick(View view){

        String key;
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();

        boolean networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

        if (!networkCheck) {
            Toast.makeText(this, R.string.check_internet, Toast.LENGTH_SHORT).show();
        } else {
            switch (view.getId()){

                case R.id.select_bus :
                    key = KEY_BUS;
                    bundle.putString(SEARCH_KEY, key);
                    searchFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_layout, searchFragment ).addToBackStack(SEARCH_FRAGMENT).commit();
                    break;
                case R.id.search_route :
                    key = KEY_ROUTE;
                    bundle.putString(SEARCH_KEY, key);
                    searchFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_layout, searchFragment ).addToBackStack(SEARCH_FRAGMENT).commit();
                    break;

            }
        }

        return null;
    }
}
