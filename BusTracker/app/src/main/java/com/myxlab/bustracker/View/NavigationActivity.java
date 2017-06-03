package com.myxlab.bustracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.BaseActivity;
import com.myxlab.bustracker.Controller.BusStopAdapter;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;

public class NavigationActivity extends BaseActivity {

    public AppBarLayout appBarLayout;
    int busStopIndex;
//    private List<Bus> bus = new ArrayList<>();
//    private List<BusETA> busETAs = new ArrayList<>();
//    int pendingRequest = 0;
//    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.navigationAppBar);
        Intent intent = getIntent();
        busStopIndex = intent.getIntExtra(MainActivity.BUS_STOP_KEY,0);

        TextView current = (TextView) findViewById(R.id.search_current);
        current.setText(UserInstance.getInstance().getBusStopList().get(busStopIndex).getName());

        BusStopAdapter busStopAdapter = new BusStopAdapter(UserInstance.getInstance().getBusStopList().get(busStopIndex).getBus(), UserInstance.getInstance().getBusStopList().get(busStopIndex), getApplicationContext(), this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.navigationRv);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(busStopAdapter);
        busStopAdapter.notifyDataSetChanged();
//      progressbar = (ProgressBar) findViewById(R.id.progressBarNavigation);

    }

    public View back(View view){
        onBackPressed();
        return view;
    }

    public View searchCurrent(View view){

//        appBarLayout.setVisibility(View.GONE);
//        FragmentManager fragmentManager = getFragmentManager();
//        NavigationSearchFragment navigationSearchFragment= new NavigationSearchFragment();
//        fragmentManager.beginTransaction().replace(R.id.fragment_navigation, navigationSearchFragment).addToBackStack(null).commit();

        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();

        return view;
    }

    public View searchDestination(View view){

//        appBarLayout.setVisibility(View.GONE);
//        FragmentManager fragmentManager = getFragmentManager();
//        NavigationSearchFragment navigationSearchFragment= new NavigationSearchFragment();
//        fragmentManager.beginTransaction().replace(R.id.fragment_navigation, navigationSearchFragment).addToBackStack(null).commit();
        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();

        return view;
    }
}
