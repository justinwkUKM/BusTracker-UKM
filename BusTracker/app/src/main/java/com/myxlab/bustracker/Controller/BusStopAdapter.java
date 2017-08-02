package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.Bus;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.MapsFragment;
import com.myxlab.bustracker.View.NavigationActivity;

import java.util.ArrayList;
import java.util.List;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    public static final String TAG = BusStopAdapter.class.getSimpleName();
    private List<String> bus;
    private BusStop busStop;
    private View view;
    private NavigationActivity navigationActivity;
    private FontChangeCrawler fontChanger;
    Context context;
    MapsFragment mapsFragment;
    private List<Bus> busListAll;
    private List<Bus> busListCurrentAll;
    private List<Bus> busListSelected;
    private CurrentBusesAdapter currentBusesAdapter;
    private RecyclerView recyclerView;


    public BusStopAdapter(List<String> buses, BusStop busStop, Context context, NavigationActivity navigationActivity) {
        this.bus = buses;
        this.busStop = busStop;
        this.context = context;
        this.navigationActivity = navigationActivity;
        this.mapsFragment = new MapsFragment();
        busListCurrentAll = new ArrayList<>();
        busListSelected = new ArrayList<>();
        busListAll = UserInstance.getInstance().getBuses();

        if (!bus.isEmpty()) {
            for (int i = 0; i < bus.size(); i++) {
                createList(bus.get(i), busListAll, busListCurrentAll,"fromConstructor");
            }

        }



        Log.d(TAG, "BusStopAdapter()");

    }

    private void createList(String busName, List<Bus> busListFrom, List<Bus> busListTo, String Tag) {
        for (int i = 0; i < busListFrom.size(); i++) {
            if (busName.equals(busListFrom.get(i).getName())){
                busListTo.add(busListFrom.get(i));
            }
        }
        if (!busListTo.isEmpty()) {
            String temp = "";
            for (int i = 0; i < busListTo.size(); i++) {

                temp+= busListTo.get(i).getName()+":"+busListTo.get(i).getPlate()+"\n";
            }
            Log.e(Tag , temp);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");

        context = navigationActivity.getApplicationContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");




        return new BusStopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder()");
        fontChanger.replaceFonts((ViewGroup) this.view);
        String busName  = bus.get(position);
        holder.busName.setText("\""+busName+"\"");

        switch (busName){
            case "Bus Zone 6":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);
                busListSelected.clear();
                createList(busName, busListCurrentAll, busListSelected, "Case Z6");
                settingAdapter(busListSelected, busName);
                //currentBusesAdapter.notifyDataSetChanged();
                break;
            case "Bus Zone 2" : holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                busListSelected.clear();
                createList(busName, busListCurrentAll, busListSelected, "Case Z2");
                settingAdapter(busListSelected, busName);
                //currentBusesAdapter.notifyDataSetChanged();
                break;
            case "Bus Zone 3U":
                holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                busListSelected.clear();
                createList(busName, busListCurrentAll, busListSelected, "Case Z3u");
                settingAdapter(busListSelected, busName);
                //currentBusesAdapter.notifyDataSetChanged();
                break;
            default:  holder.imageViewBusIcon.setImageResource(R.drawable.ic_directions);
        }
    }

    private void settingAdapter(List<Bus> list, String busName) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_currentBuses);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        currentBusesAdapter = new CurrentBusesAdapter(list, context, busName, navigationActivity, busStop);
        recyclerView.setAdapter(currentBusesAdapter);
        //currentBusesAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView busName;
        RelativeLayout locate, schedule;
        ImageView imageViewBusIcon;

        ViewHolder(View itemView) {

            super(itemView);
            Log.d(TAG, "ViewHolder()");
            busName = (TextView) view.findViewById(R.id.titleBusItem);
            locate = (RelativeLayout) view.findViewById(R.id.locatebtn);
            schedule = (RelativeLayout) view.findViewById(R.id.schedulebtn);
            imageViewBusIcon = (ImageView) view.findViewById(R.id.busIcon);

        }
    }
}
