package com.myxlab.bustracker.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myxlab.bustracker.FontChangeCrawler;
import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.NavigationActivity;

import java.util.List;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    private List<String> bus;
    private BusStop busStop;
    private View view;
    private NavigationActivity navigationActivity;
    private FontChangeCrawler fontChanger;
    Context context;

    public BusStopAdapter(List<String> buses, BusStop busStop, Context context, NavigationActivity navigationActivity) {
        this.bus = buses;
        this.busStop = busStop;
        this.context = context;
        this.navigationActivity = navigationActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stop_item,null);
        fontChanger = new FontChangeCrawler(context.getAssets(), "fonts/timelessbold.ttf");

        return new BusStopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        fontChanger.replaceFonts((ViewGroup) this.view);

        holder.busName.setText(bus.get(position));
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
    }

    @Override
    public int getItemCount() {
        return bus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView busName;
        RelativeLayout locate, schedule;

        ViewHolder(View itemView) {
            super(itemView);
            busName = (TextView) view.findViewById(R.id.titleBusItem);
            locate = (RelativeLayout) view.findViewById(R.id.locatebtn);
            schedule = (RelativeLayout) view.findViewById(R.id.schedulebtn);
        }
    }
}
