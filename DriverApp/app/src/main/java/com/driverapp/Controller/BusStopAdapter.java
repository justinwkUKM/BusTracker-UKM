package com.driverapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverapp.Model.BusStop;
import com.driverapp.R;

import java.util.List;

/**
 * Created by MyXLab on 30/1/2017.
 * Adapter to feed all the available bus stops for the selected route.
 */
public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    private List<BusStop> busStopList;
    private View view;

    /**
     * Instantiates a new Bus stop adapter.
     *
     * @param busStopList the bus stop list
     */
    public BusStopAdapter(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }

    /**
     * Sets bus stop list.
     *
     * @param busStopList the bus stop list
     */
    public void setBusStopList(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }

    @Override
    public BusStopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        BusStopAdapter.ViewHolder viewHolder = new BusStopAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BusStopAdapter.ViewHolder holder, int position) {

        final BusStop busStop = busStopList.get(position);
        holder.title.setText(busStop.getName());

        if (position == getItemCount()-1){

            holder.icon.setImageResource(R.drawable.ic_end_bus_stop_now);

        } else {

            switch (position){
                case 0 :
                    holder.icon.setImageResource(R.drawable.ic_start_bus_stop_now);
                    break;
                default:
                    holder.icon.setImageResource(R.drawable.ic_next_bus_stop_now);
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return busStopList.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
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
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.route_title);
            icon = (ImageView) view.findViewById(R.id.route_icon);
        }
    }
}
