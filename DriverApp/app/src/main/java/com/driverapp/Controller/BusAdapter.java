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

/**
 * Created by MyXLab on 30/1/2017.
 * Adapter to feed all the available buses.
 */
public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

    private List<Bus> busList;
    private View view;
    private FragmentManager fragmentManager;
    private JourneyActivity journeyActivity;

    /**
     * Instantiates a new Bus adapter.
     *
     * @param busList         the bus list
     * @param fragmentManager the fragment manager
     * @param journeyActivity the journey activity
     */
    public BusAdapter(List<Bus> busList, FragmentManager fragmentManager, JourneyActivity journeyActivity) {
        this.busList = busList;
        this.fragmentManager = fragmentManager;
        this.journeyActivity = journeyActivity;
    }

    /**
     * Sets bus list.
     *
     * @param busList the bus list
     */
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

    /**
     * The type View holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Title.
         */
        TextView title;
        /**
         * The Icon.
         */
        ImageView icon;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.item_title);
            icon = (ImageView) view.findViewById(R.id.item_icon);
        }
    }
}
