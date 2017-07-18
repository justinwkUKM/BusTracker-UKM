package com.driverapp.Controller;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverapp.Model.Bus;
import com.driverapp.R;
import com.driverapp.View.JourneyActivity;

import java.util.List;


public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

    private List<Bus> busList;
    private View view;
    private FragmentManager fragmentManager;
    private JourneyActivity journeyActivity;

    public BusAdapter(List<Bus> busList, FragmentManager fragmentManager, JourneyActivity journeyActivity) {
        this.busList = busList;
        this.fragmentManager = fragmentManager;
        this.journeyActivity = journeyActivity;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        BusAdapter.ViewHolder viewHolder = new BusAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Bus bus = busList.get(position);
        holder.title.setText(bus.getBusName() + "(" + bus.getBusPlate() + ")" );
        holder.icon.setImageResource(R.drawable.ic_directions_bus);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                journeyActivity.setBus(bus);
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.item_title);
            icon = (ImageView) view.findViewById(R.id.item_icon);
        }
    }
}
