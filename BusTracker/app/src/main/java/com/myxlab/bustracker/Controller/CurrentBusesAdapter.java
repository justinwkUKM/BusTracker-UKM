package com.myxlab.bustracker.Controller;

import android.content.Context;
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

public class CurrentBusesAdapter extends RecyclerView.Adapter<CurrentBusesAdapter.ViewHolder> {


    private BusStop busStop;
    private View view;
    private NavigationActivity navigationActivity;
    private FontChangeCrawler fontChanger;
    Context context;
    private List<Bus> busList;
    private List<Bus> busListToAdd;
    private String busName;



    public CurrentBusesAdapter(List<Bus> buses, Context context, String busName) {
        this.busList = buses;
        this.context = context;
        this.busName = busName;
        busListToAdd = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item_current,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");
        return new CurrentBusesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        fontChanger.replaceFonts((ViewGroup) this.view);
        //String busName  = busList.get(position).getName();
        for (int j = 0; j < busList.size(); j++) {
            if (busList.get(j).getName().equals(busName)) {
                String busPlate  = busList.get(position).getPlate();
                String busStopJustPssed  = busList.get(position).getName();
                holder.tvPlate.setText(busPlate);
                holder.tvJustPassed.setText(busStopJustPssed);
            }
        }

        holder.locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInstance.getInstance().getMainActivity().ETABottomSheetCall(busStop.getName(),busList.get(holder.getAdapterPosition()).getName());
                navigationActivity.finish();
            }
        });

        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, R.string.under_development, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout locate, schedule;
        TextView tvPlate, tvJustPassed;

        ViewHolder(View itemView) {
            super(itemView);
            locate = (RelativeLayout) view.findViewById(R.id.locatebtn);
            schedule = (RelativeLayout) view.findViewById(R.id.schedulebtn);
            tvPlate = (TextView) view.findViewById(R.id.tvbusLocate);
            tvJustPassed = (TextView) view.findViewById(R.id.tvbusSchedule);
        }
    }
}
