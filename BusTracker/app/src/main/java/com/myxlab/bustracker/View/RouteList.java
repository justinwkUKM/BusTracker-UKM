package com.myxlab.bustracker.View;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.BaseActivity;
import com.myxlab.bustracker.Controller.BusStopAdapter;
import com.myxlab.bustracker.Controller.BusStopAdapterRoute;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.Route;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

import java.util.List;

public class RouteList extends BaseActivity {

    //testcomment
    public static final String SEARCH_FRAGMENT = "Search Fragment";
    public static final String SEARCH_KEY = "Search KEY";
    private static final String KEY_ROUTE = "Route";
    TextView selectRoute;
    LinearLayout routeOverview;
    RecyclerView routeRecyclerView;
    List<BusStop> busStopList;
    BusStopAdapterRoute busStopAdapter;
    private ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        selectRoute = (TextView) findViewById(R.id.tv_select_route);

        routeOverview = (LinearLayout) findViewById(R.id.route_list_details);

        routeRecyclerView = (RecyclerView) findViewById(R.id.route_list_details_rv);
        routeRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        routeRecyclerView.setLayoutManager(mLayoutManager);
        routeRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }



    @Override
    public void onBackPressed() {

        Intent i = new Intent(RouteList.this, MainActivity.class);
        startActivity(i);
    }



    public void setRoute(Route routeInfo){
        UserInstance.getInstance().setRoute(routeInfo);
        routeOverview.setVisibility(View.VISIBLE);
        selectRoute.setText(routeInfo.getRouteName());

        busStopList = routeInfo.getBusStopList();
        busStopAdapter = new BusStopAdapterRoute(busStopList);
        routeRecyclerView.setAdapter(busStopAdapter);
        busStopAdapter.notifyDataSetChanged();


    }

    public View onSearchClick(View view){

        String key;
        SearchFragmentRoute searchFragmentRoute = new SearchFragmentRoute();
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();

        boolean networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

        if (!networkCheck) {
            Toast.makeText(this, R.string.check_internet, Toast.LENGTH_SHORT).show();
        } else {
            switch (view.getId()){


                case R.id.search_route :
                    key = KEY_ROUTE;
                    bundle.putString(SEARCH_KEY, key);
                    searchFragmentRoute.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_layout_journey, searchFragmentRoute ).addToBackStack(SEARCH_FRAGMENT).commit();
                    break;

            }
        }

        return null;
    }

}
