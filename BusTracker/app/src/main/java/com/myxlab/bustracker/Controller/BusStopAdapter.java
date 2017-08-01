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

        if (!busListAll.isEmpty()) {
            for (int i = 0; i < busListAll.size(); i++) {
                for (int j = 0; j < buses.size(); j++)
                    if (busListAll.get(i).getName().equals(buses.get(j))) {
                    busListCurrentAll.add(busListAll.get(j));
                    Log.e("BsAdp", busListAll.get(j).getPlate());
                }

            }

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = navigationActivity.getApplicationContext();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_currentBuses);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return new BusStopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        busListSelected.clear();

        fontChanger.replaceFonts((ViewGroup) this.view);
        String busName  = bus.get(position);
        holder.busName.setText("\""+busName+"\"");
        Log.e("Bus Name", "\""+busName+"\"");
        holder.locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInstance.getInstance().getMainActivity().ETABottomSheetCall(busStop.getName(),bus.get(holder.getAdapterPosition()));
                navigationActivity.finish();
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, R.string.under_development, Toast.LENGTH_SHORT).show();
            }
        });

        switch (busName){
            case "Bus Zone 2" : holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_2);
                if (!busListCurrentAll.isEmpty()) {
                    for (int j = 0; j < busListCurrentAll.size(); j++) {
                        if (busListCurrentAll.get(j).getName().equals(busName)) {
                            busListSelected.add(busListCurrentAll.get(j));
                        }
                    }
                }
                currentBusesAdapter = new CurrentBusesAdapter(busListSelected, context, busName);
                recyclerView.setAdapter(currentBusesAdapter);
                currentBusesAdapter.notifyDataSetChanged();

                break;
            case "Bus Zone 3U" : holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_3u);
                if (!busListCurrentAll.isEmpty()) {
                    for (int j = 0; j < busListCurrentAll.size(); j++) {
                        if (busListCurrentAll.get(j).getName().equals(busName)) {
                            busListSelected.add(busListCurrentAll.get(j));
                        }
                    }
                }
                currentBusesAdapter = new CurrentBusesAdapter(busListSelected, context,busName);
                recyclerView.setAdapter(currentBusesAdapter);
                currentBusesAdapter.notifyDataSetChanged();


                break;
            case "Bus Zone 6" : holder.imageViewBusIcon.setImageResource(R.drawable.ic_bus_6);

                if (!busListCurrentAll.isEmpty()) {
                    for (int j = 0; j < busListCurrentAll.size(); j++) {
                        if (busListCurrentAll.get(j).getName().equals(busName)) {
                            busListSelected.add(busListCurrentAll.get(j));
                        }
                    }
                }
                currentBusesAdapter = new CurrentBusesAdapter(busListSelected, context,busName);
                recyclerView.setAdapter(currentBusesAdapter);
                currentBusesAdapter.notifyDataSetChanged();

                break;
            default:  holder.imageViewBusIcon.setImageResource(R.drawable.ic_directions);
        }

        /*if (!busListCurrentAll.isEmpty()) {
            for (int j = 0; j < busListCurrentAll.size(); j++) {
                if (busListCurrentAll.get(j).getName().equals(busName)) {
                    busListSelected.add(busListCurrentAll.get(j));
                }
            }
        }

        if (!busListSelected.isEmpty()) {
            currentBusesAdapter = new CurrentBusesAdapter(busListSelected, context);
            recyclerView.setAdapter(currentBusesAdapter);
            currentBusesAdapter.notifyDataSetChanged();
        }
*/
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
            busName = (TextView) view.findViewById(R.id.titleBusItem);
            locate = (RelativeLayout) view.findViewById(R.id.locatebtn);
            schedule = (RelativeLayout) view.findViewById(R.id.schedulebtn);
            imageViewBusIcon = (ImageView) view.findViewById(R.id.busIcon);

        }
    }
}
