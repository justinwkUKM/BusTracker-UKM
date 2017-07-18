package com.driverapp.Controller;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverapp.Model.Route;
import com.driverapp.R;
import com.driverapp.View.JourneyActivity;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private List<Route> routeList;
    private View view;
    private FragmentManager fragmentManager;
    private JourneyActivity journeyActivity;

    public RouteAdapter(List<Route> routeList, FragmentManager fragmentManager, JourneyActivity journeyActivity) {
        this.routeList = routeList;
        this.fragmentManager = fragmentManager;
        this.journeyActivity = journeyActivity;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        RouteAdapter.ViewHolder viewHolder = new RouteAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RouteAdapter.ViewHolder holder, int position) {

        final Route route = routeList.get(position);
        holder.title.setText(route.getRouteName());
        holder.icon.setImageResource(R.drawable.ic_directions);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                journeyActivity.setRoute(route);
                fragmentManager.popBackStack();
            }
        });

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) view.findViewById(R.id.item_title);
            icon = (ImageView) view.findViewById(R.id.item_icon);
        }
    }
}
